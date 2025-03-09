package telegram.bot.aa_run.telegram_bot.models.logModels.logAbstractions;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.Date;

@Getter
@Setter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
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

}
