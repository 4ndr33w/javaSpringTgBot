package telegram.bot.aa_run.telegram_bot.telegram.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.bot.aa_run.telegram_bot.config.BotProperties;
import telegram.bot.aa_run.telegram_bot.models.BotState;
import telegram.bot.aa_run.telegram_bot.models.enums.UserStatus;
import telegram.bot.aa_run.telegram_bot.repositories.sqlite.UserRepository;
import telegram.bot.aa_run.telegram_bot.services.MessageService;
import telegram.bot.aa_run.telegram_bot.services.RegistrationService;
import telegram.bot.aa_run.telegram_bot.telegram.commands.*;
import telegram.bot.aa_run.telegram_bot.telegram.commands.adminCommands.*;
import telegram.bot.aa_run.telegram_bot.telegram.commands.interfaces.Command;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import telegram.bot.aa_run.telegram_bot.models.sqlite.User;

@Component
public class CommandHandler {

    private Map<String, Command> commands = new HashMap<>();

    @Autowired
    public static Map<Long, BotState> botState;

    @Autowired
    private final UserRepository userRepository;
    private final RegistrationService registrationService;
    private final BotProperties botProperties;
    private final MessageService messageService;

    public CommandHandler(@Autowired StartCommand startCommand,
                          @Autowired RegisterCommand registerCommand,
                          @Autowired HelpCommand helpCommand,
                          @Autowired GetRolesCommand getRolesCommand,
                          @Autowired DeleteUserCommand deleteUserCommand,
                          @Autowired EditUserCommand editUserCommand,
                          @Autowired HideControlButtonsCommand hideControlButtonsCommand,
                          @Autowired SendMessageToAllCommand sendMessageToAllCommand,
                          @Autowired SendMessageToGroupCommand sendMessageToGroupCommand,
                          @Autowired SendMessageToUserCommand sendMessageToUserCommand,
                          @Autowired SetAdminToUserCommand setAdminToUserCommand,
                          @Autowired SetUserToAdminCommand setUserToAdminCommand,
                          @Autowired ShowControlButtonsCommand showControlButtonsCommand,
                          @Autowired ShowStatisticsCommand statisticsCommand,
                          RegistrationService registrationService,
                          UserRepository userRepository,
                          BotProperties botProperties,
                          MessageService messageService) {

        this.commands = new HashMap<>();
        this.commands.put("/start", startCommand);
        this.commands.put("/register", registerCommand);
        this.commands.put("/help", helpCommand);
        this.commands.put("/getRoles", getRolesCommand);
        this.commands.put("/удалить", deleteUserCommand);
        this.commands.put("/редактир", editUserCommand);
        this.commands.put("/СкрытьКнопки", hideControlButtonsCommand);
        this.commands.put("/СообщВсем", sendMessageToAllCommand);
        this.commands.put("/СообщениеГруппе", sendMessageToGroupCommand);
        this.commands.put("/сообщУчастнику", sendMessageToUserCommand);
        this.commands.put("/setAdminToUser", setAdminToUserCommand);
        this.commands.put("/setUserToAdmin", setUserToAdminCommand);
        this.commands.put("/Управление", showControlButtonsCommand);
        this.commands.put("/statistics", statisticsCommand);

        //initBotState();
        this.registrationService = registrationService;
        this.userRepository = userRepository;
        this.botProperties = botProperties;
        this.messageService = messageService;
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
        long mainAdminId = Long.parseLong(botProperties.getAdmin());

        if(botState == null) {
            botState = new HashMap<>();
            botState.put(chatId, new BotState());
        }

        boolean isAdmin = chatId == mainAdminId;
        boolean isUserEmpty = userRepository.findById(chatId).isEmpty();
        if(isUserEmpty) {
            if (isAdmin) {
                saveUser(update, chatId, UserStatus.ADMIN);
            } else {
                saveUser(update, chatId, UserStatus.USER);
            }
        }
        var commandHandler = commands.get(command);
        if (commandHandler != null) {
            return commandHandler.apply(update);
        } else {
            return menuHandler(update);
        }
    }


    public SendMessage menuHandler(Update update) {

        long chatId = getChatId(update);
        switch (botState.get(chatId).getCurrentCommandType()) {
            case REGISTRATION: {
                return registrationService.registrationHandler(update);
            }
            case HELP: {
                break;
            }
            case MESSAGE_TO_ALL, MESSAGE_TO_GROUP: {
                return messageService.messageHandler(update);
            }
        }
        return null;
    }

    private  void saveUser(Update update, Long userId, UserStatus userStatus) {

        String lastName = update.getMessage().getChat().getLastName();
        String firstName = update.getMessage().getChat().getFirstName();
        String fullUserName = update.getMessage().getChat().getUserName();
        String userName = fullUserName == null? lastName + " " + firstName : fullUserName;

        var user = new User(userId, userName, userStatus, new Date());

        userRepository.save(user);
    }

    private long getChatId(Update update) {
        long chatId = 0;
        if (update.getMessage() == null) {
            chatId = update.getCallbackQuery().getFrom().getId();
        } else {
            chatId = update.getMessage().getChatId();
        }
        return chatId;
    }
}
