package telegram.bot.aa_run.telegram_bot.models;

import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import telegram.bot.aa_run.telegram_bot.models.enums.UserStatus;

import java.util.Date;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends ModelBase {

    @Column(name = "telegram_id", columnDefinition = "bigint")
    private long telegramId;

    @Column(name = "full_user_name", columnDefinition = "text")
    private String fullUserName;

    @Column(name = "user_status", columnDefinition = "integer")
    private UserStatus userStatus;

    @Column(name = "created_at", columnDefinition = "timestamptz")
    private Date createdAt;
}
