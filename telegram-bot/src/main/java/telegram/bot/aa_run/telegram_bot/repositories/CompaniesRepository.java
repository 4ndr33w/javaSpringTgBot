package telegram.bot.aa_run.telegram_bot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;
import telegram.bot.aa_run.telegram_bot.models.companies.CityModel;
import telegram.bot.aa_run.telegram_bot.models.companies.CompanyModel;
import telegram.bot.aa_run.telegram_bot.models.companies.RegionModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompaniesRepository extends JpaRepository<ModelBase, Integer> {

    @Query(value = "SELECT * FROM java_spring_bot.regions", nativeQuery = true)
    public Optional<List<RegionModel>> findAllRegions();

    @Query(value = "SELECT * FROM java_spring_bot.cities WHERE region_id = ?", nativeQuery = true)
    public Optional<List<CityModel>> findAllCities(Integer regionId);

    @Query(value = "SELECT * FROM java_spring_bot.companies WHERE city_id = ?", nativeQuery = true)
    public Optional<List<CompanyModel>> findAllCompanies(Integer cityId);

    @Query(value = "SELECT * FROM java_spring_bot.companies WHERE id = ?", nativeQuery = true)
    public Optional<ModelBase> findById(Integer id);
}
