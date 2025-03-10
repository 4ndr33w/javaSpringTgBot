package telegram.bot.aa_run.telegram_bot.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;
import telegram.bot.aa_run.telegram_bot.models.enums.CompetitionType;

import java.util.ArrayList;
import java.util.List;

public class MarkupHandler {

    public static InlineKeyboardMarkup genderMarkup() {

        String maleText = "мужчина";
        String femaleText = "женщина";

        InlineKeyboardButton maleButton = new InlineKeyboardButton();
        maleButton.setText(maleText);
        maleButton.setCallbackData("male");

        InlineKeyboardButton femaleButton = new InlineKeyboardButton();
        femaleButton.setText(femaleText);
        femaleButton.setCallbackData("female");

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(maleButton);
        row.add(femaleButton);

        return new InlineKeyboardMarkup(List.of(row));
    }

    public static InlineKeyboardMarkup collectionMarkup(List<ModelBase> collection) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (ModelBase region : collection) {
            rows.add(newRowButton(region.getName(), String.valueOf(region.getId())));
        }
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup eventTypeMarkup() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        rows.add(newRowButton(Constants.COMPETITION_TYPE01_DESCRIPTION, String.valueOf(CompetitionType.TYPE_1)));
        rows.add(newRowButton(Constants.COMPETITION_TYPE02_DESCRIPTION, String.valueOf(CompetitionType.TYPE_2)));
        rows.add(newRowButton(Constants.COMPETITION_TYPE03_DESCRIPTION, String.valueOf(CompetitionType.TYPE_3)));
        rows.add(newRowButton(Constants.COMPETITION_TYPE04_DESCRIPTION, String.valueOf(CompetitionType.TYPE_4)));
        rows.add(newRowButton(Constants.COMPETITION_TYPE05_DESCRIPTION, String.valueOf(CompetitionType.TYPE_5)));

        markup.setKeyboard(rows);
        return markup;
    }

    private static List<InlineKeyboardButton> newRowButton(String buttonDescription, String callbackData) {

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonDescription);
        button.setCallbackData(callbackData);

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);
        return row;
    }

    public static ReplyKeyboardMarkup getBasicReplyMarkup() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("/register"));
        row1.add(new KeyboardButton("/help"));

        keyboard.setKeyboard(List.of(row1));

        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(true);
        keyboard.setSelective(true);

        return keyboard;
    }
}
