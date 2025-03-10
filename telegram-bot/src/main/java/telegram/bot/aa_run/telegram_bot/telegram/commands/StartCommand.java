package telegram.bot.aa_run.telegram_bot.telegram.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import telegram.bot.aa_run.telegram_bot.telegram.commands.interfaces.Command;
import telegram.bot.aa_run.telegram_bot.utils.MarkupHandler;

import java.util.Arrays;
import java.util.List;

@Component
public class StartCommand implements Command {

    @Override
    public SendMessage apply(Update update) {

        long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("Start Message!");
        sendMessage.setReplyMarkup(MarkupHandler.getBasicReplyMarkup());
        return sendMessage;
    }

    private void addKeyboard(SendMessage sendMessage){
        sendMessage.setReplyMarkup(MarkupHandler.getBasicReplyMarkup());
    }
}
