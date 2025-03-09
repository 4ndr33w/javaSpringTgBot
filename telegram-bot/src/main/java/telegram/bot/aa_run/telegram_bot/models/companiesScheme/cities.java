package telegram.bot.aa_run.telegram_bot.models.companiesScheme;

import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;

import lombok.NoArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;

@Entity
@NoArgsConstructor
@Table(name = "cities")
public class cities extends ModelBase {

    @Column(name = "region_id", columnDefinition = "bigint")
    private long regionId;
}
