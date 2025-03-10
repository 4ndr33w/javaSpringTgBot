package telegram.bot.aa_run.telegram_bot.models.logModels;

import lombok.*;
import telegram.bot.aa_run.telegram_bot.models.logModels.logAbstractions.LogModelBase;

import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Table(name = "errors", schema = "java_spring_bot")
public class ErrorLog extends LogModelBase {

    public ErrorLog() {
        super();
    }
}
