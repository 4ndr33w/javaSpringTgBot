package telegram.bot.aa_run.telegram_bot.repositories.postgre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;
import telegram.bot.aa_run.telegram_bot.models.companies.RegionModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<ModelBase, Integer> {

    @Query(value = "SELECT * FROM java_spring_bot.regions", nativeQuery = true)
    public Optional<List<RegionModel>> findAllRegions();
}
