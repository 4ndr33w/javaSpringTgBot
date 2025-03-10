package telegram.bot.aa_run.telegram_bot.models;

import telegram.bot.aa_run.telegram_bot.models.enums.*;

public class BotState {

    private ActiveCommandType currentCommandType = ActiveCommandType.DEFAULT;
    private MenuStep menuStep = MenuStep.DEFAULT;
    private CompetitorModel competitorModel;

    private int regionId;
    private int cityId;

    public ActiveCommandType getCurrentCommandType() {
        return currentCommandType;
    }
    public void setCurrentCommandType(ActiveCommandType currentCommandType) {
        this.currentCommandType = currentCommandType;
    }
    public MenuStep getRegistrationStep() {
        return menuStep;
    }
    public void setRegistrationStep(MenuStep menuStep) {
        this.menuStep = menuStep;
    }
    public CompetitorModel getCompetitorModel() {
        return competitorModel;
    }
    public void setCompetitorModel(CompetitorModel competitorModel) {
        this.competitorModel = competitorModel;
    }
    public int getRegionId() {
        return regionId;
    }
    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }
    public int getCityId() {
        return cityId;
    }
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
