package ru.test.avito.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.test.avito.DTO.CallbackAdvertData;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class KeyboardFactory {

    public static final String createAnAdvert = "Create an advert";
    public static final String SeeAdverts = "See adverts";
    public static final String sellerRow3 = "sellerRow3";

    public static final String buyerRow1 = "Search";
    public static final String buyerRow2 = "Show saved";
    public static final String buyerRow3 = "buy3";

    public static final String sell = "/sell";
    public static final String buy = "/buy";
    public static final String help = "/help";
    public static final String start = "/start";
    public static final String done = "/done";
    public static final String exit = "/exit";
    public static final String next = "/next";

    public static final String edit = "Edit";
    public static final String delete = "Delete";
    public static final String save = "Save";
    public static final String removeFromSaved = "Remove";

    private KeyboardFactory() {
    }

    static ReplyKeyboardMarkup sellerKeyboard() {
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

    static ReplyKeyboardMarkup createAdvert() {
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

    static ReplyKeyboardMarkup buyerKeyboard() {
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

    static ReplyKeyboardMarkup exitKeyboard() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();

        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(exit);

        ArrayList<KeyboardRow> list = new ArrayList<>();
        list.add(keyboardRow1);
        keyboard.setKeyboard(list);
        keyboard.setOneTimeKeyboard(false);
        keyboard.setResizeKeyboard(true);

        return keyboard;
    }

    static ReplyKeyboardMarkup exitAndSkipKeyboard() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();

        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow1.add(next);
        keyboardRow2.add(exit);

        ArrayList<KeyboardRow> list = new ArrayList<>();
        list.add(keyboardRow1);
        list.add(keyboardRow2);
        keyboard.setKeyboard(list);
        keyboard.setOneTimeKeyboard(true);
        keyboard.setResizeKeyboard(true);

        return keyboard;
    }


    static ReplyKeyboardRemove keyboardRemove() {
        return new ReplyKeyboardRemove();
    }

    static InlineKeyboardMarkup inlineManageAdvertKeyboard(long advertId) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String dataEdit = "", dataDelete = "";
        try {
            dataEdit = objectMapper.writeValueAsString(new CallbackAdvertData(edit, advertId));
            dataDelete = objectMapper.writeValueAsString(new CallbackAdvertData(delete, advertId));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        keyboardRow1.add(new InlineKeyboardButton()
                .setCallbackData(dataEdit)
                .setText(edit));
        keyboardRow1.add(new InlineKeyboardButton()
                .setCallbackData(dataDelete)
                .setText(delete));
        keyboard.add(keyboardRow1);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    static InlineKeyboardMarkup inlineManageAdvertInSavedKeyboard(long advertId) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String dataDelete = "";
        try {
            dataDelete = objectMapper
                    .writeValueAsString(new CallbackAdvertData(removeFromSaved, advertId));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        keyboardRow1.add(new InlineKeyboardButton()
                .setCallbackData(dataDelete)
                .setText(removeFromSaved));
        keyboard.add(keyboardRow1);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    static InlineKeyboardMarkup inlineSaveAdvertKeyboard(long advertId) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String dataSave = "";
        try {
            dataSave = objectMapper.writeValueAsString(new CallbackAdvertData(save, advertId));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        keyboardRow1.add(new InlineKeyboardButton()
                .setCallbackData(dataSave)
                .setText(save));
        keyboard.add(keyboardRow1);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

}
