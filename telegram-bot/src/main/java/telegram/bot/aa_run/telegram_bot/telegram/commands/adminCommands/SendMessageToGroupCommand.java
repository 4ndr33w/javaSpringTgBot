package telegram.bot.aa_run.telegram_bot.telegram.commands.adminCommands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.bot.aa_run.telegram_bot.models.BotState;
import telegram.bot.aa_run.telegram_bot.models.enums.ActiveCommandType;
import telegram.bot.aa_run.telegram_bot.models.enums.MenuStep;
import telegram.bot.aa_run.telegram_bot.models.enums.UserStatus;
import telegram.bot.aa_run.telegram_bot.repositories.postgre.CompetitorRepository;
import telegram.bot.aa_run.telegram_bot.repositories.sqlite.UserRepository;
import telegram.bot.aa_run.telegram_bot.services.MessageService;
import telegram.bot.aa_run.telegram_bot.telegram.commands.interfaces.Command;
import telegram.bot.aa_run.telegram_bot.telegram.handlers.CommandHandler;

@Component
public class SendMessageToGroupCommand implements Command {

    private final CompetitorRepository competitorRepository;
    private final UserRepository userRepository;
    private final MessageService messageService;

    public SendMessageToGroupCommand(CompetitorRepository competitorRepository, UserRepository userRepository, MessageService messageService) {
        this.competitorRepository = competitorRepository;
        this.userRepository = userRepository;
        this.messageService = messageService;
    }
    @Override
    public SendMessage apply(Update update) {

        long chatId = update.getMessage().getChatId();

        var currentUser = userRepository.findById(chatId).get();
        if(currentUser.getUserStatus().equals((UserStatus.ADMIN)))
        {
            BotState state = CommandHandler.botState.get(chatId);

            if(state == null) {
                state = new BotState();
                state.setMenuStep(MenuStep.DEFAULT);
                CommandHandler.botState.put(chatId, state);
            }
            state.setCurrentCommandType(ActiveCommandType.MESSAGE_TO_GROUP);

            return messageService.messageHandler(update);
        }
        else {
            return null;
        }
    }
}
