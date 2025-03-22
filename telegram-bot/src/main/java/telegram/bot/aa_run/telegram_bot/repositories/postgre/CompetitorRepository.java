package telegram.bot.aa_run.telegram_bot.repositories.postgre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import telegram.bot.aa_run.telegram_bot.models.enums.CompetitionType;
import telegram.bot.aa_run.telegram_bot.models.postgre.CompetitorModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompetitorRepository extends JpaRepository<CompetitorModel, Long> {

    @Query(value = "SELECT * FROM java_spring_bot.competitors WHERE telegram_id = ?", nativeQuery = true)
    public Optional<CompetitorModel> findById(long id);

    @Query(value = "SELECT * FROM java_spring_bot.competitors WHERE competition_type = ?", nativeQuery = true)
    public Optional<List<CompetitorModel>> findByCompetitionType(CompetitionType competitionType);
}
