package telegram.bot.aa_run.telegram_bot.models.companiesScheme;

import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;

import lombok.NoArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;

@Entity
@Table(name = "cities", schema = "java_spring_bot")
public class Cities extends ModelBase {

    @Column(name = "region_id", columnDefinition = "bigint")
    private long regionId;

    public Cities() {}
    public Cities(long id, String name, long regionId) {
        super(id, name);
        this.regionId = regionId;
    }

    public long getRegionId() {
        return regionId;
    }
    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }
}
