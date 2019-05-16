package ru.test.avito.domain;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "adverts_in_progress")
public class AdvertInProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 1023)
    private String text;
    @Column
    private ArrayList<String> photos;
    @Column(nullable = false)
    private Integer hostId;

    public AdvertInProgress(String text, ArrayList<String> photos, Integer hostId) {
        this.text = text;
        this.photos = photos;
        this.hostId = hostId;
    }

    public AdvertInProgress() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public void addPhoto(String photo) {
        if (photos == null) {
            photos = new ArrayList<>();
        }
        photos.add(photo);
    }

    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

    @Override
    public String toString() {
        return "Advert{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", photos=" + photos +
                ", hostId='" + hostId + '\'' +
                '}';
    }
}
