package telegram.bot.aa_run.telegram_bot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;
import telegram.bot.aa_run.telegram_bot.models.companies.CompanyModel;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyModel, Integer> {
    @Query(value = "SELECT * FROM java_spring_bot.companies WHERE id = ?", nativeQuery = true)
    public Optional<CompanyModel> findById(Integer id);
}
