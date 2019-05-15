package ru.test.avito.service.model;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.test.avito.bot.TestBot;
import ru.test.avito.domain.UserEntity;
import ru.test.avito.pipeline.PipeState;
import ru.test.avito.repository.AdvertRepository;
import ru.test.avito.repository.UserRepository;
import ru.test.avito.service.MessageFactory;

@Service
public class PipeManager {

    private AdvertRepository advertRepository;
    private UserRepository userRepository;
    private TestBot testBot;

    public PipeManager(AdvertRepository advertRepository, UserRepository userRepository, TestBot testBot) {
        this.advertRepository = advertRepository;
        this.userRepository = userRepository;
        this.testBot = testBot;
    }

    void moveThrough(Update update) {
        UserEntity userEntity = userRepository.findByUserId(update.getMessage().getFrom().getId()).get();
        if (update.hasMessage() && update.getMessage().hasText()) {
            switch (update.getMessage().getText()) {
                case "/help":
                    userEntity.setPipeState(PipeState.None);
                    try {
                        testBot.execute(MessageFactory.getHelp(update.getMessage().getChatId().toString()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case "/sell":
                    userEntity.setPipeState(PipeState.Seller);
                    try {
                        testBot.execute(MessageFactory.greetSeller(update.getMessage().getChatId().toString()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case "/buy":
                    userEntity.setPipeState(PipeState.Buyer);
                    try {
                        testBot.execute(MessageFactory.greetBuyer(update.getMessage().getChatId().toString()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        switch (userEntity.getPipeState()) {
            case Seller:
                //todo: create keyborards, and send them to user when he presses /sell or /buy.
                break;
            case Buyer:
                break;
        }
    }
}
