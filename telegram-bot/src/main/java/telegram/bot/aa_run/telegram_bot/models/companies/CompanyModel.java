package telegram.bot.aa_run.telegram_bot.models.companies;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;

@Entity
@Table(name = "companies")
public class CompanyModel extends ModelBase {

    private long cityId;

    protected CompanyModel() {} // Для JPA

    private CompanyModel(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.cityId = builder.cityId;
    }

    public long getCityId() {
        return cityId;
    }
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long id;
        private String name;
        private long cityId;

        public Builder() {
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder regionId(long cityId) {
            this.cityId = cityId;
            return this;
        }

        public CompanyModel build() {
            return new CompanyModel(this);
        }
    }
}
