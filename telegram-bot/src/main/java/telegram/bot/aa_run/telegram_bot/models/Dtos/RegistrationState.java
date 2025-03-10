package telegram.bot.aa_run.telegram_bot.models.Dtos;

import telegram.bot.aa_run.telegram_bot.models.enums.MenuStep;

public class RegistrationState {

    private String name;
    private Integer age;
    private String gender;
    private String region;
    private String city;
    private String company;
    private String competitionType;

    private MenuStep step;

    public RegistrationState() {
        this.step = MenuStep.NAME;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getCompetitionType() {
        return competitionType;
    }
    public void setCompetitionType(String competitionType) {
        this.competitionType = competitionType;
    }
    public MenuStep getStep() {
        return step;
    }
    public void setStep(MenuStep step) {
        this.step = step;
    }
}
