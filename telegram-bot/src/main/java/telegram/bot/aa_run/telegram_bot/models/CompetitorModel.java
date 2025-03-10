package telegram.bot.aa_run.telegram_bot.models;

import java.util.Date;

import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;
import telegram.bot.aa_run.telegram_bot.models.enums.CompetitionType;

@Entity
@Table(name = "competitors", schema = "java_spring_bot")
public class CompetitorModel extends ModelBase {

    @Column(name = "telegram_id", columnDefinition = "bigint")
    private long telegramId;

    @Column(name = "company", columnDefinition = "text")
    private String company;

    @Column(name = "gender", columnDefinition = "character")
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

    public CompetitorModel() {}
    public CompetitorModel(long id, String name, long telegramId, String company, char gender, int age, double totalResult, Date createdAt, Date updatedAt, CompetitionType competitionType) {
        super(id, name);
        this.telegramId = telegramId;
        this.company = company;
        this.gender = gender;
        this.age = age;
        this.totalResult = totalResult;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.competitionType = competitionType;
    }

    public long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(long telegramId) {
        this.telegramId = telegramId;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public char getGender() {
        return gender;
    }
    public void setGender(char gender) {
        this.gender = gender;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public double getTotalResult() {
        return totalResult;
    }
    public void setTotalResult(double totalResult) {
        this.totalResult = totalResult;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    public CompetitionType getCompetitionType() {
        return competitionType;
    }
    public void setCompetitionType(CompetitionType competitionType) {
        this.competitionType = competitionType;
    }

    @Override
    public String toString() {
        return new String(this.name + " - " + this.company + ";\n" + this.totalResult + " - " + this.competitionType);
    }
}
