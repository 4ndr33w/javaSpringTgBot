package telegram.bot.aa_run.telegram_bot.models.logModels.logAbstractions;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MappedSuperclass;

import java.util.Date;

@MappedSuperclass
public class LogModelBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigserial")
    protected long id;

    @Column(name = "message", columnDefinition = "text")
    protected String message;

    @Column(name = "telegram_id", columnDefinition = "bigint")
    protected long telegramId;

    @Column(name = "created_at", columnDefinition = "timestamptz")
    protected Date createdAt;

    public LogModelBase() {}
    public LogModelBase(long id, String message, long telegramId, Date createdAt) {
        this.id = id;
        this.message = message;
        this.telegramId = telegramId;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public long getTelegramId() {
        return telegramId;
    }
    public void setTelegramId(long telegramId) {
        this.telegramId = telegramId;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
