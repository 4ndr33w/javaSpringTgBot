package telegram.bot.aa_run.telegram_bot.models.companies;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;

@Entity
@Table(name = "cities")
public class CityModel extends ModelBase {

    private int regionId;

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }
}
