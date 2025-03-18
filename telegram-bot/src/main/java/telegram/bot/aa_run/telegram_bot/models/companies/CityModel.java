package telegram.bot.aa_run.telegram_bot.models.companies;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jdk.jshell.Snippet;
import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;

@Entity
@Table(name = "cities")
public class CityModel extends ModelBase {

    private long regionId;

    protected CityModel() {} // Для JPA

    private CityModel(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.regionId = builder.regionId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public long getRegionId() {
        return regionId;
    }
    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public static class Builder {
        private long id;
        private String name;
        private long regionId;

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

        public Builder regionId(long regionId) {
            this.regionId = regionId;
            return this;
        }

        public CityModel build() {
            return new CityModel(this);
        }
    }
}
