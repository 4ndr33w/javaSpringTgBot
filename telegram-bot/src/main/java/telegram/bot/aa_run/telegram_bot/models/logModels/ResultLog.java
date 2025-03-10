package telegram.bot.aa_run.telegram_bot.models.logModels;

import telegram.bot.aa_run.telegram_bot.models.logModels.logAbstractions.LogModelBase;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.Date;

@Entity
@Table(name = "results", schema = "java_spring_bot")
public class ResultLog extends LogModelBase {

    @Column(name = "total_result", columnDefinition = "numeric")
    private double totalResult;

    @Column(name = "last_added_result", columnDefinition = "numeric")
    private double lastAddedResult;

    public ResultLog() {
        super();
    }

    public ResultLog(long id, String message, long telegramId, Date createdAt, double totalResult, double lastAddedResult) {
        super(id, message, telegramId, createdAt);
        this.totalResult = totalResult;
        this.lastAddedResult = lastAddedResult;
    }

    public double getTotalResult() {
        return totalResult;
    }
    public void setTotalResult(double totalResult) {
        this.totalResult = totalResult;
    }
    public double getLastAddedResult() {
        return lastAddedResult;
    }
    public void setLastAddedResult(double lastAddedResult) {
        this.lastAddedResult = lastAddedResult;
    }
}
