package ru.test.avito.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.test.avito.credential.TokenHandler;
import ru.test.avito.service.model.UpdateManager;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestBot extends TelegramLongPollingBot {

    private String token;
    private Integer messageId;
    private UpdateManager updateManager;

    public TestBot() {
        token = TokenHandler.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateManager.update(update);
    }


    public UpdateManager getUpdateManager() {
        return updateManager;
    }

    @Autowired
    public void setUpdateManager(UpdateManager updateManager) {
        this.updateManager = updateManager;
    }

    @Override
    public String getBotUsername() {
        return "itsLikeAvitoBot";
    }

    @Override
    public String getBotToken() {
        return token;
    }


    private void stuff(Update update) {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("1");
        keyboardRow.add("2");
        ArrayList<KeyboardRow> list = new ArrayList<>();
        list.add(keyboardRow);
        keyboard.setKeyboard(list);
        keyboard.setOneTimeKeyboard(true);
        keyboard.setResizeKeyboard(true);


        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> inlineList = new ArrayList<>();
        ArrayList<InlineKeyboardButton> inlineList1 = new ArrayList<>();
        List<List<InlineKeyboardButton>> mark = new ArrayList<>();
        inlineList.add(new InlineKeyboardButton("2").setUrl("http://2.com"));
        inlineList1.add(new InlineKeyboardButton("3").setUrl("http://3.com"));
        inlineList1.add(new InlineKeyboardButton("4").setCallbackData("AYAYA"));


        mark.add(inlineList);
        mark.add(inlineList1);
        inlineKeyboard.setKeyboard(mark);

        System.out.println(update.getMessage().getFrom().toString());
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                SendMessage message = new SendMessage();
                if (update.getMessage().getText().equals("1")) {
                    System.out.println("1!");
                    message.setChatId(update.getMessage().getChatId())
                            .setText(update.getMessage().getText());
                } else {
                    message.setChatId(update.getMessage().getChatId())
                            .setText(update.getMessage().getText())
                            .setReplyMarkup(keyboard)
                            .setReplyMarkup(inlineKeyboard);

                }
                try {
                    if (messageId != null) {
                        DeleteMessage deleteMessage = new DeleteMessage();
                        deleteMessage.setChatId(update.getMessage().getChatId())
                                .setMessageId(messageId);
                        execute(deleteMessage);
                    }
                    messageId = execute(message).getMessageId(); // Call method to send the message
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
