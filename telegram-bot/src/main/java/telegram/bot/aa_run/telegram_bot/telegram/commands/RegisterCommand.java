package telegram.bot.aa_run.telegram_bot.telegram.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.bot.aa_run.telegram_bot.models.BotState;
import telegram.bot.aa_run.telegram_bot.models.enums.ActiveCommandType;
import telegram.bot.aa_run.telegram_bot.models.enums.MenuStep;
import telegram.bot.aa_run.telegram_bot.repositories.postgre.CompetitorRepository;
import telegram.bot.aa_run.telegram_bot.repositories.sqlite.UserRepository;
import telegram.bot.aa_run.telegram_bot.services.RegistrationService;
import telegram.bot.aa_run.telegram_bot.telegram.commands.interfaces.Command;
import telegram.bot.aa_run.telegram_bot.telegram.handlers.CommandHandler;

@Component
public class RegisterCommand implements Command {

    private final RegistrationService registrationService;
    private final CompetitorRepository competitorRepository;
    private final UserRepository userRepository;

    public RegisterCommand(RegistrationService registrationService, CompetitorRepository competitorRepository, UserRepository userRepository) {
        this.registrationService = registrationService;
        this.competitorRepository = competitorRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SendMessage apply(Update update) {
        long chatId = update.getMessage().getChatId();

        //var currentUser = userRepository.findById(chatId).get();
        BotState state = CommandHandler.botState.get(chatId);

        if(state == null) {
            state = new BotState();
            state.setMenuStep(MenuStep.DEFAULT);
            CommandHandler.botState.put(chatId, state);
        }
        state.setCurrentCommandType(ActiveCommandType.REGISTRATION);

        return registrationService.startRegistrationProcess(update);
    }
}
