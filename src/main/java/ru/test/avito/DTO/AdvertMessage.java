package ru.test.avito.DTO;

import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class AdvertMessage {
    private SendMessage message;
    private SendMediaGroup photos;

    public AdvertMessage(SendMessage message, SendMediaGroup photos) {
        this.message = message;
        this.photos = photos;
    }

    public AdvertMessage() {
    }

    public SendMessage getMessage() {
        return message;
    }

    public void setMessage(SendMessage message) {
        this.message = message;
    }

    public SendMediaGroup getPhotos() {
        return photos;
    }

    public void setPhotos(SendMediaGroup photos) {
        this.photos = photos;
    }

    public boolean hasPhotos() {
        return photos != null;
    }


}
