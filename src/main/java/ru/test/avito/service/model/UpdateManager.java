package ru.test.avito.service.model;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.test.avito.bot.MarketplaceBot;
import ru.test.avito.repository.AdvertRepository;
import ru.test.avito.repository.UserRepository;

import javax.annotation.PostConstruct;

@Service
public class UpdateManager {

    private final AdvertRepository advertRepository;
    private final UserRepository userRepository;
    private final MarketplaceBot marketplaceBot;
    private final PipeManager pipeManager;

    public UpdateManager(AdvertRepository advertRepository, UserRepository userRepository, MarketplaceBot marketplaceBot,
                         PipeManager pipeManager) {
        this.advertRepository = advertRepository;
        this.userRepository = userRepository;
        this.marketplaceBot = marketplaceBot;
        this.pipeManager = pipeManager;
    }

    @Async
    public void update(Update update) {
        pipeManager.moveThrough(update);
    }

    @PostConstruct
    protected void init() {
        marketplaceBot.setUpdateManager(this);
    }
}
