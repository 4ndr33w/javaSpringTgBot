package telegram.bot.aa_run.telegram_bot.telegram.callbacks.interfaces;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Callback {
    SendMessage apply(Callback callback, Update update);
}
