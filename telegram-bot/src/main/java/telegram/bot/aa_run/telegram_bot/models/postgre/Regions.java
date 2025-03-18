package telegram.bot.aa_run.telegram_bot.models.postgre;

import jakarta.persistence.*;
import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;

@Entity
//@NoArgsConstructor
@Table(name = "regions", schema = "java_spring_bot")
public class Regions extends ModelBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected long id;

    @Column(name = "name", columnDefinition = "text")
    protected String name;

    public Regions() {}
    public Regions(long id, String name) {
        this.id = id;
        this.name = name;
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
