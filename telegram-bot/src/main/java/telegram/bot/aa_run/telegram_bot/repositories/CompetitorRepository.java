package telegram.bot.aa_run.telegram_bot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import telegram.bot.aa_run.telegram_bot.models.CompetitorModel;
import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;

import java.util.Optional;

public interface CompetitorRepository extends JpaRepository<CompetitorModel, Long> {

    @Query(value = "SELECT * FROM java_spring_bot.competitors WHERE telegram_id = ?", nativeQuery = true)
    public Optional<CompetitorModel> findById(long id);
}
