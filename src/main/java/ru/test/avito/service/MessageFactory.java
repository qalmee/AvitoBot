package ru.test.avito.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

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
                .setText("Send the text of advert and some photos. When you are done just type /done")
                .setReplyMarkup(KeyboardFactory.createAdvert())
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

}
