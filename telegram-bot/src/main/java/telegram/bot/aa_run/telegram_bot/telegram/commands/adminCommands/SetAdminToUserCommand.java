package telegram.bot.aa_run.telegram_bot.telegram.commands.adminCommands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.bot.aa_run.telegram_bot.telegram.commands.interfaces.Command;

@Component
public class SetAdminToUserCommand implements Command {
    @Override
    public SendMessage apply(Update update) {
        return null;
    }
}
