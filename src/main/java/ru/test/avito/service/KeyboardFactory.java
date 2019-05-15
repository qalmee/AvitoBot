package ru.test.avito.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class KeyboardFactory {

    public static final String createAnAdvert = "Create an advert";
    public static final String SeeAdverts = "See adverts";
    public static final String sellerRow3 = "sellerRow3";

    public static final String buyerRow1 = "buy1";
    public static final String buyerRow2 = "buy2";
    public static final String buyerRow3 = "buy3";

    public static final String sell = "/sell";
    public static final String buy = "/buy";
    public static final String help = "/help";
    public static final String start = "/start";
    public static final String done = "/done";

    private KeyboardFactory() {
    }

    public static ReplyKeyboardMarkup sellerKeyboard() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();

        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        keyboardRow1.add(createAnAdvert);
        keyboardRow2.add(SeeAdverts);
        keyboardRow3.add(sellerRow3);

        ArrayList<KeyboardRow> list = new ArrayList<>();
        list.add(keyboardRow1);
        list.add(keyboardRow2);
        list.add(keyboardRow3);
        keyboard.setKeyboard(list);
        keyboard.setOneTimeKeyboard(true);
        keyboard.setResizeKeyboard(true);

        return keyboard;
    }

    public static ReplyKeyboardMarkup createAdvert() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();

        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(done);

        ArrayList<KeyboardRow> list = new ArrayList<>();
        list.add(keyboardRow1);
        keyboard.setKeyboard(list);
        keyboard.setOneTimeKeyboard(true);
        keyboard.setResizeKeyboard(true);

        return keyboard;
    }

    public static ReplyKeyboardMarkup buyerKeyboard() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();

        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        keyboardRow1.add(buyerRow1);
        keyboardRow2.add(buyerRow2);
        keyboardRow3.add(buyerRow3);

        ArrayList<KeyboardRow> list = new ArrayList<>();
        list.add(keyboardRow1);
        list.add(keyboardRow2);
        list.add(keyboardRow3);
        keyboard.setKeyboard(list);
        keyboard.setOneTimeKeyboard(true);
        keyboard.setResizeKeyboard(true);

        return keyboard;
    }

    public static ReplyKeyboardRemove keyboardRemove() {
        return new ReplyKeyboardRemove();
    }

}
