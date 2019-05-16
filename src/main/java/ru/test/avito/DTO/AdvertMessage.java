package ru.test.avito.DTO;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.util.ArrayList;
import java.util.List;

public class AdvertMessage {
    private SendMessage message;
    private List<SendPhoto> photos;

    public AdvertMessage(SendMessage message, List<SendPhoto> photos) {
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

    public List<SendPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<SendPhoto> photos) {
        this.photos = photos;
    }

    public void addPhoto(SendPhoto photoId) {
        if (photos == null) {
            photos = new ArrayList<>();
        }
        photos.add(photoId);
    }

    public boolean hasPhotos() {
        return photos != null && !photos.isEmpty();
    }


}
