package telegram.bot.aa_run.telegram_bot.utils;

import telegram.bot.aa_run.telegram_bot.models.companies.CityModel;
import telegram.bot.aa_run.telegram_bot.models.companies.CompanyModel;
import telegram.bot.aa_run.telegram_bot.models.postgre.Cities;
import telegram.bot.aa_run.telegram_bot.models.postgre.Companies;

public class Mapper {

    public static CityModel toCityModel(Cities cityEntity) {
        return CityModel.builder()
                .id(cityEntity.getId())
                .name(cityEntity.getName())
                .regionId(cityEntity.getRegionId())
                .build();
    }

    public  static CompanyModel toCompanyModel(Companies companyEntity) {
        return CompanyModel.builder()
                .id(companyEntity.getId())
                .name(companyEntity.getName())
                .build();
    }
}
