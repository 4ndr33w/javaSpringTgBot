package telegram.bot.aa_run.telegram_bot.models.postgre;

import jakarta.persistence.*;
import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;

@Entity
//@NoArgsConstructor
@Table(name = "companies", schema = "java_spring_bot")
public class Companies extends ModelBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected long id;

    @Column(name = "name", columnDefinition = "text")
    protected String name;
    @Column(name = "city_id", columnDefinition = "bigint")
    private long cityId;

    public Companies() {}

    public long getCityId() {
        return cityId;
    }
    public void setCityId(long cityId) {
        this.cityId = cityId;
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
