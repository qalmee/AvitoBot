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

    public static SendMessage startSell(String chatId) {
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

    public static SendMessage startBuy(String chatId) {
        return new SendMessage()
                .setText("Very well. This is catalog. Hope you will like it.")
                .setReplyMarkup(KeyboardFactory.buyerKeyboard())
                .setChatId(chatId);
    }

    public static SendMessage startSearch(String chatId) {
        return new SendMessage()
                .setText("Type in your search query.")
                .setReplyMarkup(KeyboardFactory.exitKeyboard())
                .setChatId(chatId);
    }

    //dto
    public static List<AdvertMessage> advertMessages(String chatId, List<Advert> adverts, boolean editable) {
        List<AdvertMessage> advertMessages = new ArrayList<>();
        for (Advert advert : adverts) {
            advertMessages.add(advertToMessage(chatId, advert, editable));
        }
        return advertMessages;
    }

    public static SendMessage ownAdvertsWelcome(String chatId) {
        return new SendMessage()
                .setText("This is your adverts.\n")
                .setChatId(chatId);

    }

    public static SendMessage searchAdvertsWelcome(String chatId) {
        return new SendMessage()
                .setText("Search results:\n")
                .setChatId(chatId);
    }

    public static SendMessage noSearchAdverts(String chatId) {
        return new SendMessage()
                .setText("No adverts was found.\n")
                .setChatId(chatId);
    }

    public static SendMessage divider(String chatId) {
        return new SendMessage()
                .setText("----------\n----------")
                .setChatId(chatId);

    }

    public static SendMessage noAdverts(String chatId) {
        return new SendMessage()
                .setText("You have no adverts.")
                .setReplyMarkup(KeyboardFactory.keyboardRemove())
                .setChatId(chatId);
    }

    public static SendMessage advertDeleted(String chatId) {
        return new SendMessage()
                .setText("Advert deleted.")
                .setChatId(chatId);
    }

    public static SendMessage editAdvert(String chatId) {
        return new SendMessage()
                .setText("Enter new advert text. If you do not want to change it, type /next.")
                .setReplyMarkup(KeyboardFactory.exitAndSkipKeyboard())
                .setChatId(chatId);
    }

    public static SendMessage editAdvertPhotos(String chatId) {
        return new SendMessage()
                .setText("Send new advert photos. When you are done or do not want to change it type /next.")
                .setReplyMarkup(KeyboardFactory.exitAndSkipKeyboard())
                .setChatId(chatId);
    }

    public static SendMessage discardEdit(String chatId) {
        return new SendMessage()
                .setText("All changes have been discarded.")
                .setReplyMarkup(KeyboardFactory.keyboardRemove())
                .setChatId(chatId);
    }

    public static SendMessage editFinished(String chatId) {
        return new SendMessage()
                .setText("All changes have been saved.")
                .setReplyMarkup(KeyboardFactory.keyboardRemove())
                .setChatId(chatId);
    }


    public static AdvertMessage advertToMessage(String chatId, Advert advert, boolean editable) {
        AdvertMessage message = new AdvertMessage();
        SendMessage sendMessage = new SendMessage();
        if (editable) {
            sendMessage.setReplyMarkup(KeyboardFactory.inlineManageAdvertKeyboard(advert.getId()));
        }
        message.setMessage(sendMessage
                .setChatId(chatId)
                .setText(advert.getText()));
        if (advert.getPhotos() != null) {
            List<InputMedia> photos = new ArrayList<>();
            for (String photoId : advert.getPhotos()) {
                photos.add(new InputMediaPhoto().setMedia(photoId).setCaption(advert.getText()));
            }
            SendMediaGroup sendMediaGroup = new SendMediaGroup();
            sendMediaGroup.setChatId(chatId).setMedia(photos);
            message.setPhotos(sendMediaGroup);
        }
        return message;
    }

}
