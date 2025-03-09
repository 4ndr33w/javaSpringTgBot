package telegram.bot.aa_run.telegram_bot.models;

import java.util.Date;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;
import telegram.bot.aa_run.telegram_bot.models.enums.CompetitionType;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "competitors")
public class CompetitorModel extends ModelBase {

    @Column(name = "telegram_id", columnDefinition = "bigint")
    private long telegramId;

    @Column(name = "company", columnDefinition = "text")
    private String company;

    @Column(name = "gender", columnDefinition = "char")
    private char gender;

    @Column(name = "age", columnDefinition = "int")
    private int age;

    @Column(name = "total_result", columnDefinition = "numeric")
    private double totalResult;

    @Column(name = "created_at", columnDefinition = "timestamptz")
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition = "timestamptz")
    private Date updatedAt;

    @Column(name = "competition_type", columnDefinition = "integer")
    private CompetitionType competitionType;

}
