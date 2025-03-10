package telegram.bot.aa_run.telegram_bot.models;

import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;

import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import telegram.bot.aa_run.telegram_bot.models.enums.UserStatus;

import java.util.Date;

@Entity
@Table(name = "users", schema = "java_spring_bot")
public class User extends ModelBase {

    @Column(name = "telegram_id", columnDefinition = "bigint")
    private long telegramId;

    @Column(name = "full_user_name", columnDefinition = "text")
    private String fullUserName;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_status", columnDefinition = "integer")
    private UserStatus userStatus = UserStatus.USER;

    @Column(name = "created_at", columnDefinition = "timestamptz")
    private Date createdAt;

    public User() {}
    public User(long id, String name, long telegramId, String fullUserName, UserStatus userStatus, Date createdAt) {
        super(id, name);
        this.telegramId = telegramId;
        this.fullUserName = fullUserName;
        this.userStatus = userStatus;
        this.createdAt = createdAt;
    }

    public long getTelegramId() {
        return telegramId;
    }
    public void setTelegramId(long telegramId) {
        this.telegramId = telegramId;
    }
    public String getFullUserName() {
        return fullUserName;
    }
    public void setFullUserName(String fullUserName) {
        this.fullUserName = fullUserName;
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
