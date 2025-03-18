package telegram.bot.aa_run.telegram_bot.telegram.commands.adminCommands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.bot.aa_run.telegram_bot.repositories.sqlite.UserRepository;
import telegram.bot.aa_run.telegram_bot.telegram.commands.interfaces.Command;
import telegram.bot.aa_run.telegram_bot.utils.MarkupHandler;

@Component
public class HideControlButtonsCommand implements Command {

    private final UserRepository userRepository;

    public HideControlButtonsCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public SendMessage apply(Update update) {
        long chatId = update.getMessage().getChatId();
        var currentUser = userRepository.findById(chatId).orElse(null);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("Скрыть кнопки управления");
        sendMessage.setReplyMarkup(MarkupHandler.getBasicReplyMarkup(currentUser.getUserStatus()));
        return sendMessage;
    }
}
