package ru.test.avito.DTO;

import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class AdvertMessage {
    private SendMessage message;
    private SendMediaGroup photos;
    private SendMessage inlineEdit;

    public AdvertMessage(SendMessage message, SendMediaGroup photos, SendMessage inlineEdit) {
        this.message = message;
        this.photos = photos;
        this.inlineEdit = inlineEdit;
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

    public SendMessage getInlineEdit() {
        return inlineEdit;
    }

    public void setInlineEdit(SendMessage inlineEdit) {
        this.inlineEdit = inlineEdit;
    }
}
