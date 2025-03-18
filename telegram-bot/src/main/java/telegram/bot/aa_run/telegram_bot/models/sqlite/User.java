package telegram.bot.aa_run.telegram_bot.models.sqlite;

import jakarta.persistence.*;

import telegram.bot.aa_run.telegram_bot.models.enums.UserStatus;

import java.util.Date;

@Entity
//@Table(name = "users")
public class User{

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_status")
    private UserStatus userStatus = UserStatus.USER;

    @Column(name = "created_at")
    private Date createdAt;

    public User() {
        userStatus = UserStatus.USER;
    }
    public User(long id, String name, UserStatus userStatus, Date createdAt) {
       this.id = id;
        this.name = name;
        this.userStatus = userStatus;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public UserStatus getUserStatus() {
        return userStatus;
    }
    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
