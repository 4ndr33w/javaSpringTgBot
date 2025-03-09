package telegram.bot.aa_run.telegram_bot.models.companiesScheme;

import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;

import lombok.NoArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;

@Entity
@NoArgsConstructor
@Table(name = "companies")
public class companies extends ModelBase {

    @Column(name = "city_id", columnDefinition = "bigint")
    private long cityId;
}
