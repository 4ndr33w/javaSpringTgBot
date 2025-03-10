package telegram.bot.aa_run.telegram_bot.telegram.commands.interfaces;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    SendMessage apply(Update update);
}
