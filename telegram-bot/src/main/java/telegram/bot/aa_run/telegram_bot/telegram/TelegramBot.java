package telegram.bot.aa_run.telegram_bot.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.bot.aa_run.telegram_bot.config.BotProperties;
import telegram.bot.aa_run.telegram_bot.models.BotState;
import telegram.bot.aa_run.telegram_bot.telegram.handlers.CallbackHandler;
import telegram.bot.aa_run.telegram_bot.telegram.handlers.CommandHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j

@Component
public class TelegramBot extends TelegramLongPollingBot {

    public final BotProperties botProperties;

    private DefaultBotOptions botOptions;
    private final String botToken;
    private CommandHandler commandHandler;
    private CallbackHandler callbacksHandler;




    public TelegramBot(BotProperties botProperties, CommandHandler commandHandler, CallbackHandler callbacksHandler) {

        super();
        botOptions = new DefaultBotOptions();
        this.botProperties = botProperties;
        botToken = String.valueOf(botProperties.getToken());
        this.commandHandler = commandHandler;
        this.callbacksHandler = callbacksHandler;

    }


    @Override
    public String getBotUsername() {
        return botProperties.getName();
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasCallbackQuery()) {

            long chatId = update.getCallbackQuery().getFrom().getId();
            var messageId = update.getCallbackQuery().getMessage().getMessageId();
            var callbackQuery = update.getCallbackQuery().getData();

            try {
                removeMarkup(chatId, messageId);
            } catch (TelegramApiException e) {
                //throw new RuntimeException(e);
            }
            sendMessage(callbacksHandler.handleCallbacks(update));
        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            if (update.getMessage().getText().startsWith("/")) {
                sendMessage(commandHandler.handleCommands(update));
            } else {
                sendMessage(commandHandler.menuHandler(update));
                //sendMessage(new SendMessage(chatId, "Данная команда не поддерживается."));
            }
        }
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            //log.error(e.getMessage());
        }
    }

    private void removeMarkup(long chatId, int messageId) throws TelegramApiException {

        EditMessageReplyMarkup sendMessage = new EditMessageReplyMarkup();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setReplyMarkup(null);
        sendMessage.setMessageId((int)messageId);

        execute(sendMessage);
    }
}
