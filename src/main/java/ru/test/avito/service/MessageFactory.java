package ru.test.avito.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class MessageFactory {
    private MessageFactory() {

    }

    public static SendMessage getGreeting(String chatId) {
        return new SendMessage()
                .setText("Hi new user!")
                .setChatId(chatId);
    }

    public static SendMessage getGreetingOld(String chatId) {
        return new SendMessage()
                .setText("Hi old user!")
                .setChatId(chatId);
    }

    public static SendMessage getHelp(String chatId) {
        return new SendMessage()
                .setText("This is very useful help to help you with your hopefully helpful situation.")
                .setChatId(chatId);
    }

    public static SendMessage greetSeller(String chatId) {
        return new SendMessage()
                .setText("Sell anything you want.")
                .setChatId(chatId);
    }

    public static SendMessage greetBuyer(String chatId) {
        return new SendMessage()
                .setText("Very well. This is catalog. Hope you will like it.")
                .setChatId(chatId);
    }

}
