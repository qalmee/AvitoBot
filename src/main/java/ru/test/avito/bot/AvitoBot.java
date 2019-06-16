package ru.test.avito.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.test.avito.credential.TokenHandler;
import ru.test.avito.service.model.UpdateManager;

@Component
public class AvitoBot extends TelegramLongPollingBot {

    private String token;
    private Integer messageId;
    private UpdateManager updateManager;

    public AvitoBot() {
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
}
