package telegram.bot.aa_run.telegram_bot.repositories.postgre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import telegram.bot.aa_run.telegram_bot.models.postgre.Companies;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Companies, Integer> {

    @Query(value = "SELECT * FROM java_spring_bot.companies WHERE id = ?", nativeQuery = true)
    public Optional<Companies> findById(Integer id);

    @Query(value = "SELECT * FROM java_spring_bot.companies WHERE city_id = ?", nativeQuery = true)
    public Optional<List<Companies>> findAllCompanies(Integer cityId);
}
