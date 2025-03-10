package telegram.bot.aa_run.telegram_bot.models.companiesScheme;

import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;

import lombok.NoArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;

@Entity
//@NoArgsConstructor
@Table(name = "companies", schema = "java_spring_bot")
public class Companies extends ModelBase {

    @Column(name = "city_id", columnDefinition = "bigint")
    private long cityId;

    public Companies() {}

    public long getCityId() {
        return cityId;
    }
    public void setCityId(long cityId) {
        this.cityId = cityId;
    }
}
