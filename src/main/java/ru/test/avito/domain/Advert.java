package ru.test.avito.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "adverts")
@Indexed
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adverts_gen")
    @SequenceGenerator(name = "adverts_gen", sequenceName = "adverts_seq")
    private Long id;
    @Column(nullable = false, length = 1023)
    @Field(termVector = TermVector.YES)
    private String text;
    @Column
    private ArrayList<String> photos;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "host_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity host;

    public Advert(String text, ArrayList<String> photos, UserEntity host) {
        this.text = text;
        this.photos = photos;
        this.host = host;
    }

    public Advert(AdvertInProgress advertInProgress) {
        this.text = advertInProgress.getText();
        this.photos = advertInProgress.getPhotos();
        this.host = advertInProgress.getHost();
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

    public UserEntity getHost() {
        return host;
    }

    public void setHost(UserEntity host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "Advert{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", photos=" + photos +
                ", host=" + host +
                '}';
    }
}
