package telegram.bot.aa_run.telegram_bot.models.logModels;

import telegram.bot.aa_run.telegram_bot.models.logModels.logAbstractions.LogModelBase;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "errors")
public class ErrorLog extends LogModelBase {
}
