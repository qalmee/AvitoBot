package ru.test.avito.domain;


import org.telegram.telegrambots.meta.api.objects.User;
import ru.test.avito.pipeline.PipeState;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    }

    public UserEntity(Integer userId, String firstName, String lastName, String userName, Boolean isBot, PipeState pipeState) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.isBot = isBot;
        this.pipeState = pipeState;
    }

    public UserEntity(Long id, Integer userId, String firstName, String lastName, String userName, Boolean isBot, PipeState pipeState) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.isBot = isBot;
        this.pipeState = pipeState;
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
                '}';
    }
}


