package ru.test.avito.service.model;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.test.avito.bot.AvitoBot;
import ru.test.avito.repository.AdvertRepository;
import ru.test.avito.repository.UserRepository;

@Service
public class UpdateManager {

    private final AdvertRepository advertRepository;
    private final UserRepository userRepository;
    private final AvitoBot avitoBot;
    private final PipeManager pipeManager;

    public UpdateManager(AdvertRepository advertRepository, UserRepository userRepository, AvitoBot avitoBot,
                         PipeManager pipeManager) {
        this.advertRepository = advertRepository;
        this.userRepository = userRepository;
        this.avitoBot = avitoBot;
        this.pipeManager = pipeManager;
    }

    public void update(Update update) {
        pipeManager.moveThrough(update);
    }
}
