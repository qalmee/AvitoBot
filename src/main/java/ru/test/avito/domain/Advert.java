package ru.test.avito.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "adverts")
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 1023)
    private String text;
    @Column
    private ArrayList<String> photos;
    @Column(nullable = false)
    private String hostId;

    public Advert(String text, ArrayList<String> photos, String hostId) {
        this.text = text;
        this.photos = photos;
        this.hostId = hostId;
    }

    public Advert() {
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

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
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
