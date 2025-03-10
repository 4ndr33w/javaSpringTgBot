package telegram.bot.aa_run.telegram_bot.models.companiesScheme;

import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;

import lombok.NoArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;

@Entity
//@NoArgsConstructor
@Table(name = "regions", schema = "java_spring_bot")
public class Regions extends ModelBase {

    public  Regions() {
    }
}
