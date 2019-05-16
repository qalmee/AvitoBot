package ru.test.avito.service.model;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.test.avito.bot.TestBot;
import ru.test.avito.domain.UserEntity;
import ru.test.avito.pipeline.PipeState;
import ru.test.avito.repository.UserRepository;
import ru.test.avito.service.KeyboardFactory;
import ru.test.avito.service.MessageFactory;

@Service
public class PipeManager {

    private AdvertManager advertManager;
    private UserRepository userRepository;
    private MessageSender messageSender;
    private TestBot testBot;

    public PipeManager(AdvertManager advertManager, UserRepository userRepository, MessageSender messageSender, TestBot testBot) {
        this.advertManager = advertManager;
        this.userRepository = userRepository;
        this.messageSender = messageSender;
        this.testBot = testBot;
    }

    void moveThrough(Update update, UserEntity userEntity) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                moveThroughWithTextMessage(update, userEntity);
            } else if (update.getMessage().hasPhoto()) {
                moveThroughWithPhotoMessage(update, userEntity);
            }
        }
    }

    private void moveThroughWithTextMessage(Update update, UserEntity userEntity) {
        if (checkForUnconditionalTransition(update, userEntity)) {
            return;
        }
        final String messageText = update.getMessage().getText();
        switch (userEntity.getPipeState()) {
            case Seller:
                switch (messageText) {
                    case KeyboardFactory.createAnAdvert:
                        advertManager.abortAdvert(userEntity); //remove advertInProgress if exists from previous attempt
                        try {
                            testBot.execute(MessageFactory.createAdvert(update.getMessage().getChatId().toString()));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        userEntity.setPipeState(PipeState.CreateAdvert);
                        break;
                    case KeyboardFactory.SeeAdverts:
                        messageSender.sendAllAdvertsByHostId(userEntity, update.getMessage().getChatId());
                        userEntity.setPipeState(PipeState.CheckOwnAdverts);
                        break;
                    case KeyboardFactory.sellerRow3:
                        break;
                }
                break;
            case CreateAdvert:
                if (messageText.equals(KeyboardFactory.done)) {
                    userEntity.setPipeState(PipeState.None);
                } else {
                    advertManager.createAnAdvert(messageText, userEntity);
                    userEntity.setPipeState(PipeState.AddPhotosToAdvert);
                    try {
                        testBot.execute(MessageFactory.attachPhotosToAdvert(update.getMessage().getChatId().toString()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case AddPhotosToAdvert:
                if (messageText.equals(KeyboardFactory.done)) {
                    advertManager.advertFinished(userEntity);
                    try {
                        testBot.execute(MessageFactory.advertDone(update.getMessage().getChatId().toString()));
                        testBot.execute(MessageFactory.sellStart(update.getMessage().getChatId().toString()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    userEntity.setPipeState(PipeState.Seller);
                }
                break;
            case SeeAdverts:
                break;
            case Buyer:
                break;
        }
    }

    private void moveThroughWithPhotoMessage(Update update, UserEntity userEntity) {
        PhotoSize photo = update.getMessage().getPhoto().get(update.getMessage().getPhoto().size() - 1);
        if (userEntity.getPipeState() == PipeState.AddPhotosToAdvert) {
            advertManager.addPhotoToAdvert(photo.getFileId(), userEntity);
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
