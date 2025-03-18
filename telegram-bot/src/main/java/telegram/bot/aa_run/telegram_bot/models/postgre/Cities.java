package telegram.bot.aa_run.telegram_bot.models.postgre;

import jakarta.persistence.*;
import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;

@Entity
@Table(name = "cities", schema = "java_spring_bot")
public class Cities/* extends ModelBase */{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected long id;

    @Column(name = "name", columnDefinition = "text")
    protected String name;

    @Column(name = "region_id", columnDefinition = "bigint")
    private long regionId;

    public Cities() {}
    public Cities(long id, String name, long regionId) {
        //super(id, name);
        this.regionId = regionId;
    }

    public long getRegionId() {
        return regionId;
    }
    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
