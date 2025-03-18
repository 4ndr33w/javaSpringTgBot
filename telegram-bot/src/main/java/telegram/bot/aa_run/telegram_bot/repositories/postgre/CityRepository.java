package telegram.bot.aa_run.telegram_bot.repositories.postgre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import telegram.bot.aa_run.telegram_bot.models.postgre.Cities;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository  extends JpaRepository<Cities, Integer> {

    @Query(value = "SELECT * FROM java_spring_bot.cities WHERE region_id = ?", nativeQuery = true)
    public Optional<List<Cities>> findAllCities(Integer regionId);
}
