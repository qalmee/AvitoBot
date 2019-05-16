package ru.test.avito.service.model;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.test.avito.bot.TestBot;
import ru.test.avito.domain.UserEntity;
import ru.test.avito.repository.AdvertRepository;
import ru.test.avito.repository.UserRepository;

@Service
public class UpdateManager {

    private AdvertRepository advertRepository;
    private UserRepository userRepository;
    private TestBot testBot;
    private PipeManager pipeManager;


    public UpdateManager(AdvertRepository advertRepository,
                         UserRepository userRepository, TestBot testBot, PipeManager pipeManager) {
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
        UserEntity userEntity;
        if (userRepository.existsByUserId(update.getMessage().getFrom().getId())) {
            userEntity = userRepository.findByUserId(update.getMessage().getFrom().getId()).get();
        } else {
            userEntity = new UserEntity(update.getMessage().getFrom());
        }
        pipeManager.moveThrough(update, userEntity);
        userRepository.save(userEntity);
    }

}
