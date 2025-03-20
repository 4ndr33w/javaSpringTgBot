package telegram.bot.aa_run.telegram_bot.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.bot.aa_run.telegram_bot.config.BotProperties;
import telegram.bot.aa_run.telegram_bot.models.BotState;
import telegram.bot.aa_run.telegram_bot.models.enums.ActiveCommandType;
import telegram.bot.aa_run.telegram_bot.models.enums.MenuStep;
import telegram.bot.aa_run.telegram_bot.models.postgre.CompetitorModel;
import telegram.bot.aa_run.telegram_bot.repositories.postgre.CompetitorRepository;
import telegram.bot.aa_run.telegram_bot.services.MessageService;
import telegram.bot.aa_run.telegram_bot.telegram.handlers.CallbackHandler;
import telegram.bot.aa_run.telegram_bot.telegram.handlers.CommandHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j

@Component
public class TelegramBot extends TelegramLongPollingBot {

    public final BotProperties botProperties;

    private final String botToken;
    @Autowired
    private final CommandHandler commandHandler;
    @Autowired
    private final CallbackHandler callbacksHandler;
    private final CompetitorRepository competitorRepository;
    private final MessageService messageService;

    public TelegramBot(BotProperties botProperties, CommandHandler commandHandler, CallbackHandler callbacksHandler, CompetitorRepository competitorRepository, MessageService messageService) {

        super();
        DefaultBotOptions botOptions = new DefaultBotOptions();
        this.botProperties = botProperties;
        botToken = String.valueOf(botProperties.getToken());
        this.commandHandler = commandHandler;
        this.callbacksHandler = callbacksHandler;
        this.competitorRepository = competitorRepository;
        this.messageService = messageService;
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
            //var callbackQuery = update.getCallbackQuery().getData();
            try {
                removeMarkup(chatId, messageId);
            } catch (TelegramApiException e) {
                //throw new RuntimeException(e);
            }
            sendMessage(callbacksHandler.handleCallbacks(update));
        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            if (update.getMessage().getText().startsWith("/")) {
                sendMessage(commandHandler.handleCommands(update));
            } else {
                var state = CommandHandler.botState.get(chatId);

                var isCurrentStateMessageToAll = state.getCurrentCommandType().equals(ActiveCommandType.MESSAGE_TO_ALL);
                var isCurrentStateStartAction = state.getMenuStep().equals(MenuStep.START_ACTION);

                if(isCurrentStateMessageToAll && isCurrentStateStartAction)
                {
                    List<CompetitorModel> competitors = competitorRepository.findAll();
                    List<Long > chatIds = competitors.stream().map(CompetitorModel::getTelegramId).toList();

                    var message = messageService.messageHandler(update);
                    try {
                        state.setMenuStep(MenuStep.DEFAULT);
                        state.setCurrentCommandType(ActiveCommandType.DEFAULT);
                        sendMessage(message, chatIds);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                sendMessage(commandHandler.menuHandler(update));
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

    private void sendMessage(SendMessage sendMessage, List<Long> chatIds) throws TelegramApiException {
        final int MAX_MESSAGES_PER_MINUTE = 20;
        final int DELAY_BETWEEN_MESSAGES = 3000;

        String adminWhoSendMessageId = sendMessage.getChatId();
        execute(sendMessage);

        String messageText ="Сообщение от администратора:\n"  + sendMessage.getText();
        for (Long competitorId : chatIds) {
            sendMessage.setChatId(String.valueOf(competitorId));

            try {
                Thread.sleep(DELAY_BETWEEN_MESSAGES);

                sendMessage.setText(messageText);
                execute(sendMessage);

            } catch (InterruptedException | TelegramApiException e) {
                sendMessage.setChatId(adminWhoSendMessageId);
                execute(sendMessage);
                e.printStackTrace();
            }
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
