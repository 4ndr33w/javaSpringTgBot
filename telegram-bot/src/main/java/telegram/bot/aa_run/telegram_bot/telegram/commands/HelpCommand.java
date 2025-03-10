package telegram.bot.aa_run.telegram_bot.telegram.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.bot.aa_run.telegram_bot.telegram.commands.interfaces.Command;
import telegram.bot.aa_run.telegram_bot.utils.MarkupHandler;

@Component
public class HelpCommand implements Command {
    @Override
    public SendMessage apply(Update update) {
        long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("Help Message!");
        sendMessage.setReplyMarkup(MarkupHandler.getBasicReplyMarkup());
        return sendMessage;
    }
}
