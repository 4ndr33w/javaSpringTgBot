package telegram.bot.aa_run.telegram_bot.models.companies;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;

@Entity
@Table(name = "companies")
public class CompanyModel extends ModelBase {

    private int cityId;

    public int getCityId() {
        return cityId;
    }
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
