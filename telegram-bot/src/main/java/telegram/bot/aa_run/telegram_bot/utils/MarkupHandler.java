package telegram.bot.aa_run.telegram_bot.utils;

import org.springframework.lang.Nullable;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;
import telegram.bot.aa_run.telegram_bot.models.enums.CompetitionType;
import telegram.bot.aa_run.telegram_bot.models.enums.UserStatus;

import java.util.ArrayList;
import java.util.List;

public class MarkupHandler {

    public static InlineKeyboardMarkup genderMarkup() {
        List<InlineKeyboardButton> row = new ArrayList<>();

        row.add(addNewInlineButton("мужчина", "male"));
        row.add(addNewInlineButton("женщина", "female"));

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

    public static InlineKeyboardMarkup getEventTypeMarkup() {
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

    private static InlineKeyboardButton addNewInlineButton(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        return button;
    }

    public static ReplyKeyboardMarkup getBasicReplyMarkup(@Nullable UserStatus userStatus) {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("/register"));
        row1.add(new KeyboardButton("/help"));

        if(userStatus.equals((UserStatus.ADMIN))) {
            KeyboardRow row2 = new KeyboardRow();

            row2.add(new KeyboardButton("/Управление"));

            keyboard.setKeyboard(List.of(row1, row2));
            keyboard.setKeyboard(List.of(row1, row2));
        }
        else {
            keyboard.setKeyboard(List.of(row1));
        }
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(true);
        keyboard.setSelective(true);

        return keyboard;
    }

    public static ReplyKeyboardMarkup getControlReplyMarkup(@Nullable UserStatus userStatus) {

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("/register"));
        row1.add(new KeyboardButton("/help"));

        if(userStatus.equals((UserStatus.ADMIN))) {

            KeyboardRow row2 = new KeyboardRow();
            row2.add(new KeyboardButton("/СообщениеГруппе"));
            row2.add(new KeyboardButton("/stat"));

            KeyboardRow row3 = new KeyboardRow();
            row3.add(new KeyboardButton("/СообщУчасчтнику"));
            row3.add(new KeyboardButton("/СообщВсем"));

            KeyboardRow row4 = new KeyboardRow();
            row4.add(new KeyboardButton("/Редактировать"));
            row4.add(new KeyboardButton("/Удалить"));

            KeyboardRow row5 = new KeyboardRow();
            row5.add(new KeyboardButton("/СкрытьКнопки"));

            keyboard.setKeyboard(List.of(row2, row3, row4, row5));
        }
        else {
            keyboard.setKeyboard(List.of(row1));
        }
        return keyboard;
    }

    public static InlineKeyboardMarkup getContinueOrCancelMarkup() {
        List<InlineKeyboardButton> row = new ArrayList<>();

        row.add(addNewInlineButton("Продолжить", "continue"));
        row.add(addNewInlineButton("Отмена", "cancel"));

        return new InlineKeyboardMarkup(List.of(row));
    }
}
