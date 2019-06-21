package ru.test.avito.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.test.avito.credential.TokenHandler;
import ru.test.avito.service.model.UpdateManager;

@Component
public class MarketplaceBot extends TelegramLongPollingBot {

    private String token;
    private UpdateManager updateManager;

    public MarketplaceBot() {
        token = TokenHandler.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateManager.update(update);
    }

    public UpdateManager getUpdateManager() {
        return updateManager;
    }

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
