package ru.test.avito.service.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.test.avito.DTO.CallbackAdvertData;
import ru.test.avito.bot.TestBot;
import ru.test.avito.dao.AdvertDao;
import ru.test.avito.domain.Advert;
import ru.test.avito.domain.UserEntity;
import ru.test.avito.pipeline.PipeState;
import ru.test.avito.repository.AdvertRepository;
import ru.test.avito.repository.UserRepository;
import ru.test.avito.service.KeyboardFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PipeManager {

    private final AdvertCreationManager advertCreationManager;
    private final UserRepository userRepository;
    private final AdvertRepository advertRepository;
    private final MessageSender messageSender;
    private final AdvertDao advertDao;
    private final TestBot testBot;
    private final ObjectMapper objectMapper;

    public PipeManager(AdvertCreationManager advertCreationManager, UserRepository userRepository,
                       AdvertRepository advertRepository, MessageSender messageSender,
                       AdvertDao advertDao, TestBot testBot) {
        this.advertCreationManager = advertCreationManager;
        this.userRepository = userRepository;
        this.advertRepository = advertRepository;
        this.messageSender = messageSender;
        this.advertDao = advertDao;
        this.testBot = testBot;
        advertDao.initializeHibernateSearch();
        objectMapper = new ObjectMapper();
    }

    void moveThrough(Update update) {
        UserEntity userEntity = null;
        if (update.hasMessage()) {
            if (userRepository.existsByUserId(update.getMessage().getFrom().getId())) {
                userEntity = userRepository.findByUserId(update.getMessage().getFrom().getId());
            } else {
                userEntity = new UserEntity(update.getMessage().getFrom());
            }
            if (update.getMessage().hasText()) {
                moveThroughWithTextMessage(update, userEntity);
            } else if (update.getMessage().hasPhoto()) {
                moveThroughWithPhotoMessage(update, userEntity);
            }
        } else if (update.hasCallbackQuery()) {
            userEntity = userRepository.findByUserId(update.getCallbackQuery().getFrom().getId());
            moveThroughWithCallbackQuery(update, userEntity);
        }
        if (userEntity != null) {
            userRepository.save(userEntity);
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
                        advertCreationManager.abortAdvert(userEntity); //remove advertInProgress if exists from previous attempt
                        messageSender.createAdvert(update.getMessage().getChatId().toString());
                        userEntity.setPipeState(PipeState.CreateAdvert);
                        break;
                    case KeyboardFactory.SeeAdverts:
                        messageSender.sendAllAdvertsByHostId(userEntity, update.getMessage().getChatId().toString());
                        break;
                    case KeyboardFactory.sellerRow3:
                        break;
                }
                break;
            case CreateAdvert:
                if (messageText.equals(KeyboardFactory.done)) {
                    userEntity.setPipeState(PipeState.None);
                } else {
                    advertCreationManager.createAnAdvert(messageText, userEntity);
                    messageSender.attachPhotosToAdvert(update.getMessage().getChatId().toString());
                    userEntity.setPipeState(PipeState.AddPhotosToAdvert);
                }
                break;
            case AddPhotosToAdvert:
                if (messageText.equals(KeyboardFactory.done)) {
                    advertCreationManager.advertFinished(userEntity);
                    messageSender.advertDone(update.getMessage().getChatId().toString());
                    messageSender.sendStartSell(update.getMessage().getChatId().toString());
                    userEntity.setPipeState(PipeState.Seller);
                }
                break;
            case EditAdvert:
                if (messageText.equals(KeyboardFactory.next)) {
                    messageSender.editAdvertPhotos(update.getMessage().getChatId().toString());
                    userEntity.setPipeState(PipeState.EditAdvertPhotosStart);
                } else if (messageText.equals(KeyboardFactory.exit)) {
                    advertCreationManager.discardChanges(userEntity);
                    messageSender.discardEdit(update.getMessage().getChatId().toString());
                    messageSender.sendStartSell(update.getMessage().getChatId().toString());
                    userEntity.setPipeState(PipeState.Seller);
                } else {
                    advertCreationManager.editText(messageText, userEntity);
                    messageSender.editAdvertPhotos(update.getMessage().getChatId().toString());
                    userEntity.setPipeState(PipeState.EditAdvertPhotosStart);
                }
                break;
            case EditAdvertPhotosStart:
            case EditAdvertPhotos:
                if (messageText.equals(KeyboardFactory.next)) {
                    advertCreationManager.advertFinished(userEntity);
                    messageSender.editFinished(update.getMessage().getChatId().toString());
                } else if (messageText.equals(KeyboardFactory.exit)) {
                    advertCreationManager.discardChanges(userEntity);
                    messageSender.discardEdit(update.getMessage().getChatId().toString());
                }
                messageSender.sendStartSell(update.getMessage().getChatId().toString());
                userEntity.setPipeState(PipeState.Seller);
                break;
            case Buyer:
                switch (messageText) {
                    case KeyboardFactory.buyerRow1:
                        messageSender.sendStartSearch(update.getMessage().getChatId().toString());
                        userEntity.setPipeState(PipeState.SearchAdverts);
                        break;
                    case KeyboardFactory.buyerRow2:
                        break;
                }
                break;
            case SearchAdverts:
                if (messageText.equals(KeyboardFactory.exit)) {
                    messageSender.sendStartBuy(update.getMessage().getChatId().toString());
                    userEntity.setPipeState(PipeState.Buyer);
                } else {
                    List<Advert> adverts = advertDao.searchAdverts(messageText);
                    messageSender.showAdvertsSearch(adverts, update.getMessage().getChatId().toString());
                }
                break;
        }
    }

    private void moveThroughWithCallbackQuery(Update update, UserEntity userEntity) {
        System.out.println(update.getCallbackQuery().getData());
        System.out.println(update.getCallbackQuery().getMessage());
        System.out.println(update.getCallbackQuery().getId());
        System.out.println(update.getCallbackQuery().getInlineMessageId());


        CallbackAdvertData data;
        try {
            data = objectMapper.readValue(update.getCallbackQuery().getData(), CallbackAdvertData.class);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (data.getAction().equals(KeyboardFactory.edit)) {
            Optional<Advert> advert = advertRepository.findById(data.getAdvertId());
            if (!advert.isPresent()) {
                return;
            }
            advertCreationManager.startEditAdvert(advert.get());
            messageSender.editAdvert(update.getCallbackQuery().getMessage().getChatId().toString());
            userEntity.setPipeState(PipeState.EditAdvert);
        } else if (data.getAction().equals(KeyboardFactory.delete)) {
            if (advertRepository.existsById(data.getAdvertId())) {
                advertRepository.delete(advertRepository.getOne(data.getAdvertId()));
            }
            messageSender.sendAdvertDeleted(update.getCallbackQuery().getMessage().getChatId().toString());
        }

    }

    private void moveThroughWithPhotoMessage(Update update, UserEntity userEntity) {
        PhotoSize photo = update.getMessage().getPhoto().get(update.getMessage().getPhoto().size() - 1);
        if (userEntity.getPipeState() == PipeState.AddPhotosToAdvert) {
            advertCreationManager.addPhotoToAdvert(photo.getFileId(), userEntity);
        } else if (userEntity.getPipeState() == PipeState.EditAdvertPhotosStart) {
            advertCreationManager.deletePhotosFromAdvert(userEntity);
            advertCreationManager.addPhotoToAdvert(photo.getFileId(), userEntity);
            userEntity.setPipeState(PipeState.EditAdvertPhotos);
        } else if (userEntity.getPipeState() == PipeState.EditAdvertPhotos) {
            advertCreationManager.addPhotoToAdvert(photo.getFileId(), userEntity);
        }
    }


    private boolean checkForUnconditionalTransition(Update update, UserEntity userEntity) {
        switch (update.getMessage().getText()) {
            case KeyboardFactory.start:
                userEntity.setPipeState(PipeState.None);
                messageSender.sendStart(update.getMessage().getChatId().toString());
                return true;
            case KeyboardFactory.help:
                userEntity.setPipeState(PipeState.None);
                messageSender.sendHelp(update.getMessage().getChatId().toString());
                return true;
            case KeyboardFactory.sell:
                userEntity.setPipeState(PipeState.Seller);
                messageSender.sendStartSell(update.getMessage().getChatId().toString());
                return true;
            case KeyboardFactory.buy:
                userEntity.setPipeState(PipeState.Buyer);
                messageSender.sendStartBuy(update.getMessage().getChatId().toString());
                return true;
            case "/test":
                messageSender.sendAnswerToMessage(update);
//                try {
////                    testBot.execute(new SendMessage()
////                            .setChatId(update.getMessage().getChatId())
////                            .setText("hahahaha")
////                            .setReplyMarkup(KeyboardFactory.inlineManageAdvertKeyboard()));
////                } catch (TelegramApiException e) {
////                    e.printStackTrace();
////                }
//                advertDao.getAdvert();
                return true;
            case "/test1":
                advertDao.saveSome();
                return true;
        }
        return false;
    }
}
