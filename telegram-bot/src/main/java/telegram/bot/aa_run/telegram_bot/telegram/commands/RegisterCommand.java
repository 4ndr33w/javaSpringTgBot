package telegram.bot.aa_run.telegram_bot.telegram.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.bot.aa_run.telegram_bot.config.BotInit;
import telegram.bot.aa_run.telegram_bot.models.BotState;
import telegram.bot.aa_run.telegram_bot.models.enums.ActiveCommandType;
import telegram.bot.aa_run.telegram_bot.models.enums.MenuStep;
import telegram.bot.aa_run.telegram_bot.repositories.CompetitorRepository;
import telegram.bot.aa_run.telegram_bot.services.RegistrationService;
import telegram.bot.aa_run.telegram_bot.telegram.commands.interfaces.Command;
import telegram.bot.aa_run.telegram_bot.telegram.handlers.CommandHandler;
import telegram.bot.aa_run.telegram_bot.utils.UtilClass;

@Component
public class RegisterCommand implements Command {

    private final RegistrationService registrationService;
    private final CompetitorRepository competitorRepository;

    public RegisterCommand(RegistrationService registrationService, CompetitorRepository competitorRepository) {
        this.registrationService = registrationService;
        this.competitorRepository = competitorRepository;
    }

    @Override
    public SendMessage apply(Update update) {
        long chatId = update.getMessage().getChatId();

        BotState state = CommandHandler.botState.get(chatId);

        if(state == null) {
            state = new BotState();
            state.setRegistrationStep(MenuStep.DEFAULT);
            CommandHandler.botState.put(chatId, state);
        }
        state.setCurrentCommandType(ActiveCommandType.REGISTRATION);

        return registrationService.startRegistrationProcess(update);
    }
}
