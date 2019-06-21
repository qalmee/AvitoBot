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
                .setText("This bot can help you with buying and selling things.\n\n" +
                        "You can control by these commands:\n\n" +
                        "/sell - to sell something\n" +
                        "/buy - to buy something\n" +
                        "/help - to see help")
                .setReplyMarkup(KeyboardFactory.keyboardRemove())
                .setChatId(chatId);
    }

    public static SendMessage getHelp(String chatId) {
        return new SendMessage()
                .setText("This bot can help you with buying and selling things.\n\n" +
                        "You can control by these commands:\n\n" +
                        "/sell - to sell something\n" +
                        "/buy - to buy something\n" +
                        "/help - to see help")
                .setChatId(chatId);
    }

    public static SendMessage startSell(String chatId) {
        return new SendMessage()
                .setText("Choose an action.")
                .setReplyMarkup(KeyboardFactory.sellerKeyboard())
                .setChatId(chatId);
    }

    public static SendMessage createAdvert(String chatId) {
        return new SendMessage()
                .setText("Send a message with text of an advert. Then photos.")
                .setReplyMarkup(KeyboardFactory.createAdvertWithoutDone())
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
                .setText("Good. Advert is now active.")
                .setReplyMarkup(KeyboardFactory.keyboardRemove())
                .setChatId(chatId);
    }

    public static SendMessage startBuy(String chatId) {
        return new SendMessage()
                .setText("Choose an action.")
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
    public static List<AdvertMessage> advertMessagesWithSave(String chatId, List<Advert> adverts) {
        List<AdvertMessage> advertMessages = new ArrayList<>();
        for (Advert advert : adverts) {
            AdvertMessage advertMessage = advertToMessage(chatId, advert);
            advertMessages.add(setInlineSaveKeyboard(advertMessage, advert, chatId));
        }
        return advertMessages;
    }

    public static List<AdvertMessage> advertMessagesWithEdit(String chatId, List<Advert> adverts) {
        List<AdvertMessage> advertMessages = new ArrayList<>();
        for (Advert advert : adverts) {
            AdvertMessage advertMessage = advertToMessage(chatId, advert);
            advertMessages.add(setInlineEditKeyboard(advertMessage, advert, chatId));
        }
        return advertMessages;
    }

    public static List<AdvertMessage> advertMessagesWithEditInSave(String chatId, List<Advert> adverts) {
        List<AdvertMessage> advertMessages = new ArrayList<>();
        for (Advert advert : adverts) {
            AdvertMessage advertMessage = advertToMessage(chatId, advert);
            advertMessages.add(setInlineEditInSavedKeyboard(advertMessage, advert, chatId));
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

    public static SendMessage noAdvertsFromHost(String chatId) {
        return new SendMessage()
                .setText("You have no adverts.")
                .setChatId(chatId);
    }

    public static SendMessage noSavedAdverts(String chatId) {
        return new SendMessage()
                .setText("You have no adverts in featured.")
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

    public static SendMessage discardCreation(String chatId) {
        return new SendMessage()
                .setText("The advert has been discarded.")
                .setReplyMarkup(KeyboardFactory.keyboardRemove())
                .setChatId(chatId);
    }

    public static SendMessage editFinished(String chatId) {
        return new SendMessage()
                .setText("All changes have been saved.")
                .setReplyMarkup(KeyboardFactory.keyboardRemove())
                .setChatId(chatId);
    }


    private static AdvertMessage advertToMessage(String chatId, Advert advert) {
        AdvertMessage message = new AdvertMessage();
        SendMessage sendMessage = new SendMessage();
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

    private static AdvertMessage setInlineSaveKeyboard(AdvertMessage advertMessage, Advert advert, String chatId) {
        advertMessage.setInlineEdit(new SendMessage()
                .setChatId(chatId)
                .setText("Add to saved:")
                .setReplyMarkup(KeyboardFactory.inlineSaveAdvertKeyboard(advert.getId())));
        return advertMessage;
    }

    private static AdvertMessage setInlineEditKeyboard(AdvertMessage advertMessage, Advert advert, String chatId) {
        advertMessage.setInlineEdit(new SendMessage()
                .setChatId(chatId)
                .setText("Manage your advert:")
                .setReplyMarkup(KeyboardFactory.inlineManageAdvertKeyboard(advert.getId())));
        return advertMessage;
    }

    private static AdvertMessage setInlineEditInSavedKeyboard(AdvertMessage advertMessage, Advert advert, String chatId) {
        advertMessage.setInlineEdit(new SendMessage()
                .setChatId(chatId)
                .setText("Remove from saved:")
                .setReplyMarkup(KeyboardFactory.inlineManageAdvertInSavedKeyboard(advert.getId())));
        return advertMessage;
    }

}
