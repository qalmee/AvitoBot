package ru.test.avito.service.model;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.test.avito.DTO.AdvertMessage;
import ru.test.avito.bot.TestBot;
import ru.test.avito.domain.Advert;
import ru.test.avito.domain.UserEntity;
import ru.test.avito.repository.AdvertRepository;
import ru.test.avito.service.MessageFactory;

import java.util.List;

@Component
public class MessageSender {

    AdvertRepository advertRepository;
    private TestBot testBot;

    public MessageSender(TestBot testBot, AdvertRepository advertRepository) {
        this.testBot = testBot;
        this.advertRepository = advertRepository;
    }

    public void sendAllAdvertsByHostId(UserEntity host, Long chatId) {
        List<Advert> adverts = advertRepository.findAllByHost(host);
        if (adverts == null || adverts.isEmpty()) {
            sendMessage(MessageFactory.noAdverts(chatId.toString()));
            return;
        }
        sendMessage(MessageFactory.ownAdvertsWelcome(chatId.toString()));
        List<AdvertMessage> advertMessages = MessageFactory.ownAdverts(chatId.toString(), adverts);
        for (AdvertMessage advertMessage : advertMessages) {
            sendMessage(advertMessage.getMessage());
            if (advertMessage.hasPhotos()) {
                for (SendPhoto photo : advertMessage.getPhotos()) {
                    sendPhoto(photo);
                }
            }
            sendMessage(MessageFactory.divider(chatId.toString()));
        }

    }

    private void sendMessage(SendMessage message) {
        try {
            testBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendPhoto(SendPhoto photo) {
        try {
            testBot.execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
