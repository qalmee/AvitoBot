package ru.test.avito.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import ru.test.avito.DTO.AdvertMessage;
import ru.test.avito.domain.Advert;

import java.util.ArrayList;
import java.util.List;

public class MessageFactory {
    private MessageFactory() {

    }

    public static SendMessage getStart(String chatId) {
        return new SendMessage()
                .setText("This is very good bot!")
                .setReplyMarkup(KeyboardFactory.keyboardRemove())
                .setChatId(chatId);
    }

    public static SendMessage getHelp(String chatId) {
        return new SendMessage()
                .setText("This is very useful help to help you with your hopefully helpful situation.")
                .setReplyMarkup(KeyboardFactory.keyboardRemove())
                .setChatId(chatId);
    }

    public static SendMessage sellStart(String chatId) {
        return new SendMessage()
                .setText("Sell anything you want.")
                .setReplyMarkup(KeyboardFactory.sellerKeyboard())
                .setChatId(chatId);
    }

    public static SendMessage createAdvert(String chatId) {
        return new SendMessage()
                .setText("Send a message with text of an advert. Then photos.")
                .setReplyMarkup(KeyboardFactory.createAdvert())
                .setChatId(chatId);
    }

    public static SendMessage attachPhotosToAdvert(String chatId) {
        return new SendMessage()
                .setText("Good. Now send photos.\nWhen you are done just type /done")
                .setReplyMarkup(KeyboardFactory.createAdvert())
                .setChatId(chatId);
    }

    public static SendMessage advertDone(String chatId) {
        return new SendMessage()
                .setText("Cool. Advert is now active.")
                .setReplyMarkup(KeyboardFactory.keyboardRemove())
                .setChatId(chatId);
    }

    public static SendMessage seeOwnAdverts(String chatId) {
        return new SendMessage()
                .setText("own adverts")
                .setReplyMarkup(KeyboardFactory.keyboardRemove())
                .setChatId(chatId);
    }

    public static SendMessage greetBuyer(String chatId) {
        return new SendMessage()
                .setText("Very well. This is catalog. Hope you will like it.")
                .setReplyMarkup(KeyboardFactory.buyerKeyboard())
                .setChatId(chatId);
    }

    //dto
    public static List<AdvertMessage> ownAdverts(String chatId, List<Advert> adverts) {
        List<AdvertMessage> advertMessages = new ArrayList<>();
        SendMediaGroup sendMediaGroup;
        for (Advert advert : adverts) {
            AdvertMessage message = new AdvertMessage();
            message.setMessage(new SendMessage()
                    .setChatId(chatId)
                    .setReplyMarkup(KeyboardFactory.keyboardRemove())
                    .setText(advert.getText()));
            if (advert.getPhotos() != null) {
                List<InputMedia> photos = new ArrayList<>();
                for (String photoId : advert.getPhotos()) {
                    photos.add(new InputMediaPhoto().setMedia(photoId));
                }
                sendMediaGroup = new SendMediaGroup();
                sendMediaGroup.setChatId(chatId).setMedia(photos);
                message.setPhotos(sendMediaGroup);
            }
            advertMessages.add(message);
        }
        return advertMessages;
    }

    public static SendMessage ownAdvertsWelcome(String chatId) {
        return new SendMessage()
                .setText("This is your adverts.\n")
                .setReplyMarkup(KeyboardFactory.keyboardRemove())
                .setChatId(chatId);

    }

    public static SendMessage divider(String chatId) {
        return new SendMessage()
                .setText("----------\n----------\n----------")
                .setReplyMarkup(KeyboardFactory.keyboardRemove())
                .setChatId(chatId);

    }

    public static SendMessage noAdverts(String chatId) {
        return new SendMessage()
                .setText("You have no adverts.")
                .setReplyMarkup(KeyboardFactory.keyboardRemove())
                .setChatId(chatId);
    }


}
