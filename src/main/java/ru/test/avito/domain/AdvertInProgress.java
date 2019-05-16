package ru.test.avito.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "host_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity host;

    public AdvertInProgress(String text, ArrayList<String> photos, UserEntity host) {
        this.text = text;
        this.photos = photos;
        this.host = host;
    }

    public AdvertInProgress() {
    }

    public AdvertInProgress(UserEntity host) {
        this.host = host;
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

    public UserEntity getHost() {
        return host;
    }

    public void setHost(UserEntity host) {
        this.host = host;
    }

    public void addPhoto(String photo) {
        if (photos == null) {
            photos = new ArrayList<>();
        }
        photos.add(photo);
    }

    @Override
    public String toString() {
        return "AdvertInProgress{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", photos=" + photos +
                ", host=" + host +
                '}';
    }
}