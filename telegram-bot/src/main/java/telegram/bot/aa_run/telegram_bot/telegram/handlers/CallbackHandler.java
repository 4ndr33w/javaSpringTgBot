package telegram.bot.aa_run.telegram_bot.telegram.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.bot.aa_run.telegram_bot.models.CallbackModel;
import telegram.bot.aa_run.telegram_bot.models.enums.CallbackType;
import telegram.bot.aa_run.telegram_bot.services.MessageService;
import telegram.bot.aa_run.telegram_bot.services.RegistrationService;
import telegram.bot.aa_run.telegram_bot.telegram.callbacks.EventCallback;
import telegram.bot.aa_run.telegram_bot.telegram.callbacks.interfaces.Callback;
import telegram.bot.aa_run.telegram_bot.utils.JsonHandler;

import java.util.List;
import java.util.Map;

import static telegram.bot.aa_run.telegram_bot.telegram.handlers.CommandHandler.botState;

@Component
public class CallbackHandler {

    private final RegistrationService registrationService;
    private final MessageService messageService;

    public CallbackHandler(RegistrationService registrationService, MessageService messageService) {

        this.registrationService = registrationService;
        this.messageService = messageService;
    }


    public SendMessage handleCallbacks(Update update) {
        var callbackData = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getFrom().getId();
        if(!callbackData.isEmpty()){
            return menuHandler(update);
        }
        return new SendMessage(String.valueOf(chatId), "Error");
    }

    public SendMessage menuHandler(Update update) {

        long chatId = update.getCallbackQuery().getFrom().getId();
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
}
