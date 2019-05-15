package ru.test.avito.service.model;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.test.avito.bot.TestBot;
import ru.test.avito.domain.UserEntity;
import ru.test.avito.repository.AdvertRepository;
import ru.test.avito.repository.TestRepository;
import ru.test.avito.repository.UserRepository;
import ru.test.avito.service.MessageFactory;

@Component
public class UpdateManager {

    private TestRepository testRepository;
    private AdvertRepository advertRepository;
    private UserRepository userRepository;
    private TestBot testBot;
    private PipeManager pipeManager;


    public UpdateManager(TestRepository testRepository, AdvertRepository advertRepository,
                         UserRepository userRepository, TestBot testBot, PipeManager pipeManager) {
        this.testRepository = testRepository;
        this.advertRepository = advertRepository;
        this.userRepository = userRepository;
        this.testBot = testBot;
        this.pipeManager = pipeManager;
    }

    public void update(Update update) {
        if (update.hasCallbackQuery()) {
            callbackQueryUpdate(update);
        } else if (update.hasMessage()) {
            messageUpdate(update);
        }

    }

    private void callbackQueryUpdate(Update update) {

    }

    private void messageUpdate(Update update) {
        if (userRepository.existsByUserId(update.getMessage().getFrom().getId())) {
            pipeManager.moveThrough(update);
            try {
                testBot.execute(MessageFactory.getGreetingOld(update.getMessage().getChatId().toString()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        } else {
            UserEntity newUser = new UserEntity(update.getMessage().getFrom());
            userRepository.save(newUser);
            try {
                testBot.execute(MessageFactory.getGreeting(update.getMessage().getChatId().toString()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

}
