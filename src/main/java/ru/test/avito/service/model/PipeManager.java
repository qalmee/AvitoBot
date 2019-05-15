package ru.test.avito.service.model;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.test.avito.bot.TestBot;
import ru.test.avito.domain.UserEntity;
import ru.test.avito.pipeline.PipeState;
import ru.test.avito.repository.AdvertRepository;
import ru.test.avito.repository.UserRepository;
import ru.test.avito.service.KeyboardFactory;
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

    void moveThrough(Update update, UserEntity userEntity) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            moveThroughWithMessage(update, userEntity);
        }
    }

    private void moveThroughWithMessage(Update update, UserEntity userEntity) {
        if (checkForUnconditionalTransition(update, userEntity)) {
            return;
        }
        final String messageText = update.getMessage().getText();
        switch (userEntity.getPipeState()) {
            case Seller:
                switch (messageText) {
                    case KeyboardFactory.createAnAdvert:
                        userEntity.setPipeState(PipeState.CreateAdvert);
                        try {
                            testBot.execute(MessageFactory.createAdvert(update.getMessage().getChatId().toString()));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    case KeyboardFactory.SeeAdverts:
                        userEntity.setPipeState(PipeState.SeeAdverts);
                        try {
                            testBot.execute(MessageFactory.seeOwnAdverts(update.getMessage().getChatId().toString()));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    case KeyboardFactory.sellerRow3:
                        break;
                }
                break;
            //todo: create an advert
            case CreateAdvert:
                if (messageText.equals(KeyboardFactory.done)) {

                } else {

                }
            case SeeAdverts:
                break;
            case Buyer:
                break;
        }
    }


    private boolean checkForUnconditionalTransition(Update update, UserEntity userEntity) {
        switch (update.getMessage().getText()) {
            case KeyboardFactory.start:
                userEntity.setPipeState(PipeState.None);
                try {
                    testBot.execute(MessageFactory.getStart(update.getMessage().getChatId().toString()));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                return true;
            case KeyboardFactory.help:
                userEntity.setPipeState(PipeState.None);
                try {
                    testBot.execute(MessageFactory.getHelp(update.getMessage().getChatId().toString()));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                return true;
            case KeyboardFactory.sell:
                userEntity.setPipeState(PipeState.Seller);
                try {
                    testBot.execute(MessageFactory.sellStart(update.getMessage().getChatId().toString()));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                return true;
            case KeyboardFactory.buy:
                userEntity.setPipeState(PipeState.Buyer);
                try {
                    testBot.execute(MessageFactory.greetBuyer(update.getMessage().getChatId().toString()));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                return true;
        }
        return false;
    }
}
