package ru.test.avito.service.model;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.test.avito.DTO.AdvertMessage;
import ru.test.avito.bot.TestBot;
import ru.test.avito.domain.Advert;
import ru.test.avito.domain.UserEntity;
import ru.test.avito.repository.AdvertRepository;
import ru.test.avito.service.MessageFactory;

import java.util.List;

@Component
@SuppressWarnings("Duplicates")
public class MessageSender {

    private final AdvertRepository advertRepository;
    private final TestBot testBot;

    public MessageSender(TestBot testBot, AdvertRepository advertRepository) {
        this.testBot = testBot;
        this.advertRepository = advertRepository;
    }

    void sendAllAdvertsByHostId(UserEntity host, String chatId) {
        List<Advert> adverts = advertRepository.findAllByHost(host);
        if (adverts == null || adverts.isEmpty()) {
            send(MessageFactory.noAdverts(chatId));
            return;
        }
        send(MessageFactory.ownAdvertsWelcome(chatId));
        List<AdvertMessage> advertMessages = MessageFactory.advertMessages(chatId, adverts, true);
        for (AdvertMessage advertMessage : advertMessages) {
            if (advertMessage.getPhotos() != null) {
                send(advertMessage.getPhotos());
            }
            send(advertMessage.getMessage());
//            send(MessageFactory.divider(chatId));
        }

    }

    void editAdvert(String chatId) {
        send(MessageFactory.editAdvert(chatId));
    }

    void editAdvertPhotos(String chatId) {
        send(MessageFactory.editAdvertPhotos(chatId));
    }

    void discardEdit(String chatId) {
        send(MessageFactory.discardEdit(chatId));
    }

    void editFinished(String chatId) {
        send(MessageFactory.editFinished(chatId));
    }

    void createAdvert(String chatId) {
        send(MessageFactory.createAdvert(chatId));
    }

    void attachPhotosToAdvert(String chatId) {
        send(MessageFactory.attachPhotosToAdvert(chatId));
    }

    void advertDone(String chatId) {
        send(MessageFactory.advertDone(chatId));
    }

    void sendStartSearch(String chatId) {
        send(MessageFactory.startSearch(chatId));
    }

    void sendStartBuy(String chatId) {
        send(MessageFactory.startBuy(chatId));
    }

    void sendStartSell(String chatId) {
        send(MessageFactory.startSell(chatId));
    }

    void sendHelp(String chatId) {
        send(MessageFactory.getHelp(chatId));
    }

    void sendStart(String chatId) {
        send(MessageFactory.getStart(chatId));
    }

    void sendAdvertDeleted(String chatId) {
        send(MessageFactory.advertDeleted(chatId));
    }

    void sendAnswerToCallbackQuery(Update update) {
//        SendChatAction sendChatAction = new SendChatAction();
//        sendChatAction
//                .setAction(ActionType.TYPING)
//                .setChatId(update.getCallbackQuery().getMessage().getChatId());
//        send(sendChatAction);
        SendMessage sendMessage = new SendMessage()
                .setChatId(update.getCallbackQuery().getMessage().getChatId())
                .setReplyToMessageId(update.getCallbackQuery().getMessage().getMessageId())
                .setText("sendAnswerToCallbackQuery");
        send(sendMessage);
    }

    void sendAnswerToMessage(Update update) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setReplyToMessageId(update.getMessage().getMessageId())
                .setText("sendAnswerToMessage");
        send(sendMessage);
    }

    private void noAdvertsSearch(String chatId) {
        send(MessageFactory.noSearchAdverts(chatId));
    }

    void showAdvertsSearch(List<Advert> adverts, String chatId) {
        if (adverts == null || adverts.isEmpty()) {
            noAdvertsSearch(chatId);
            return;
        }
        List<AdvertMessage> messages = MessageFactory.advertMessages(chatId, adverts, false);
        send(MessageFactory.searchAdvertsWelcome(chatId));
        for (AdvertMessage advertMessage : messages) {
            if (advertMessage.getPhotos() != null) {
                send(advertMessage.getPhotos());
            }
            send(advertMessage.getMessage());
//            send(MessageFactory.divider(chatId));
        }

    }

    private void send(SendMessage message) {
        try {
            testBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void send(SendMediaGroup message) {
        try {
            testBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void send(SendPhoto photo) {
        try {
            testBot.execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void send(SendChatAction action) {
        try {
            testBot.execute(action);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

//    private void send(InlineKeyboardMarkup keyboard) {
//        try {
//            testBot.execute(keyboard);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
}
