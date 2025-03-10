package telegram.bot.aa_run.telegram_bot.telegram.callbacks;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.bot.aa_run.telegram_bot.telegram.callbacks.interfaces.Callback;

public class EventCallback implements Callback {
    @Override
    public SendMessage apply(Callback callback, Update update) {
        return null;
    }
}
