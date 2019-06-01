package ru.test.avito.domain;


import org.telegram.telegrambots.meta.api.objects.User;
import ru.test.avito.pipeline.PipeState;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "users_gen")
    @SequenceGenerator(name = "users_gen", sequenceName = "users_seq")
    private Long id;
    @Column(unique = true, nullable = false)
    private Integer userId;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String userName;
    @Column
    private Boolean isBot;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PipeState pipeState;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "saved_adverts", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Advert> saved;

    public UserEntity() {
        pipeState = PipeState.None;
    }

    public UserEntity(User user) {
        this.userId = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userName = user.getUserName();
        this.isBot = user.getBot();
        this.pipeState = PipeState.None;
        this.saved = new HashSet<>();
    }

    public UserEntity(Integer userId, String firstName, String lastName,
                      String userName, Boolean isBot, PipeState pipeState,
                      Set<Advert> saved) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.isBot = isBot;
        this.pipeState = pipeState;
        this.saved = saved;
    }

    public Set<Advert> getSaved() {
        return saved;
    }

    public void setSaved(Set<Advert> saved) {
        this.saved = saved;
    }

    public void saveOne(Advert advert) {
        if (saved == null) {
            saved = new HashSet<>();
        }
        saved.add(advert);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getBot() {
        return isBot;
    }

    public void setBot(Boolean bot) {
        isBot = bot;
    }

    public PipeState getPipeState() {
        return pipeState;
    }

    public void setPipeState(PipeState pipeState) {
        this.pipeState = pipeState;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", isBot=" + isBot +
                ", pipeState=" + pipeState +
                ", saved=" + saved +
                '}';
    }
}


