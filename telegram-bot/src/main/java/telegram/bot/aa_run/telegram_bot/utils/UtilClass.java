package telegram.bot.aa_run.telegram_bot.utils;

import telegram.bot.aa_run.telegram_bot.models.enums.CompetitionType;

import java.util.ArrayList;

public class UtilClass {

    public static String getEventTypeDescription(CompetitionType event) {
        switch (event) {
            case TYPE_1: {
                return Constants.COMPETITION_TYPE01_DESCRIPTION;
            }
            case TYPE_2: {
                return Constants.COMPETITION_TYPE02_DESCRIPTION;
            }
            case TYPE_3: {
                return Constants.COMPETITION_TYPE03_DESCRIPTION;
            }
            case TYPE_4: {
                return Constants.COMPETITION_TYPE04_DESCRIPTION;
            }
            case TYPE_5: {
                return Constants.COMPETITION_TYPE05_DESCRIPTION;
            }
        }
        return "";
    }
}