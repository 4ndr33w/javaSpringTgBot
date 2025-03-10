package telegram.bot.aa_run.telegram_bot.telegram.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.bot.aa_run.telegram_bot.models.BotState;
import telegram.bot.aa_run.telegram_bot.services.RegistrationService;
import telegram.bot.aa_run.telegram_bot.telegram.commands.HelpCommand;
import telegram.bot.aa_run.telegram_bot.telegram.commands.RegisterCommand;
import telegram.bot.aa_run.telegram_bot.telegram.commands.StartCommand;
import telegram.bot.aa_run.telegram_bot.telegram.commands.interfaces.Command;
import telegram.bot.aa_run.telegram_bot.models.enums.ActiveCommandType;

import java.util.HashMap;
import java.util.Map;

import static telegram.bot.aa_run.telegram_bot.models.enums.ActiveCommandType.HELP;

@Component
public class CommandHandler {

    private Map<String, Command> commands = new HashMap<>();

    public static Map<Long, BotState> botState;

    private final RegistrationService registrationService;

    public CommandHandler(@Autowired StartCommand startCommand,
                          @Autowired RegisterCommand registerCommand,
                          @Autowired HelpCommand helpCommand,
                          RegistrationService registrationService) {
        this.commands = Map.of("/start", startCommand,
                "/register", registerCommand,
                "/help", helpCommand);
        initBotState();
        this.registrationService = registrationService;
    }

    private void initBotState() {
        if(botState == null) {
            botState = new HashMap<>();
        }
    }

    public SendMessage handleCommands(Update update) {
        String messageText = update.getMessage().getText();
        String command = messageText.split(" ")[0];
        long chatId = update.getMessage().getChatId();

        var commandHandler = commands.get(command);
        if (commandHandler != null) {
            return commandHandler.apply(update);
        } else {
            return menuHandler(update);
            //return new SendMessage(String.valueOf(chatId), "Invalid command");
        }
    }


    public SendMessage menuHandler(Update update) {

        long chatId = update.getMessage().getChatId();
        switch (botState.get(chatId).getCurrentCommandType()) {
            case REGISTRATION: {
                return registrationService.registrationHandler(update);
            }
            case HELP: {
                break;
            }
        }
        return null;
    }
}
