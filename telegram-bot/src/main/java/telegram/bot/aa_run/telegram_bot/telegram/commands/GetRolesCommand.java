package telegram.bot.aa_run.telegram_bot.telegram.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.bot.aa_run.telegram_bot.repositories.sqlite.UserRepository;
import telegram.bot.aa_run.telegram_bot.telegram.commands.interfaces.Command;
import telegram.bot.aa_run.telegram_bot.utils.MarkupHandler;

@Component
public class GetRolesCommand implements Command {

    private final UserRepository userRepository;

    public GetRolesCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public SendMessage apply(Update update) {

        var users = userRepository.findAll();
        long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        if(!users.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();

            for (var user : users) {
                stringBuilder.append(user.getId()).append(" - ") .append(user.getName()).append(" - ").append(user.getUserStatus()).append("\n");
                sendMessage.setText(stringBuilder.toString());
            }
        }

        return sendMessage;
    }
}
