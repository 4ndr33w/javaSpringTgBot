package telegram.bot.aa_run.telegram_bot.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.bot.aa_run.telegram_bot.config.BotProperties;
import telegram.bot.aa_run.telegram_bot.models.BotState;
import telegram.bot.aa_run.telegram_bot.models.enums.ActiveCommandType;
import telegram.bot.aa_run.telegram_bot.models.enums.MenuStep;
import telegram.bot.aa_run.telegram_bot.models.enums.UserStatus;
import telegram.bot.aa_run.telegram_bot.models.postgre.CompetitorModel;
import telegram.bot.aa_run.telegram_bot.repositories.postgre.CompetitorRepository;
import telegram.bot.aa_run.telegram_bot.repositories.sqlite.UserRepository;
import telegram.bot.aa_run.telegram_bot.services.MessageService;
import telegram.bot.aa_run.telegram_bot.telegram.handlers.CallbackHandler;
import telegram.bot.aa_run.telegram_bot.telegram.handlers.CommandHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    private final UserRepository userRepository;

    @Value("${file.save.directory}")
    private String saveDirectory;

    public TelegramBot(BotProperties botProperties,
                       CommandHandler commandHandler,
                       CallbackHandler callbacksHandler,
                       CompetitorRepository competitorRepository,
                       MessageService messageService,
                       UserRepository userRepository) {
        super();
        DefaultBotOptions botOptions = new DefaultBotOptions();
        this.botProperties = botProperties;
        botToken = String.valueOf(botProperties.getToken());
        this.commandHandler = commandHandler;
        this.callbacksHandler = callbacksHandler;
        this.competitorRepository = competitorRepository;
        this.messageService = messageService;
        this.userRepository = userRepository;
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
            }
            else {
                var state = CommandHandler.botState.get(chatId);
                var isCurrentStateMessageToAll = state.getCurrentCommandType().equals(ActiveCommandType.MESSAGE_TO_ALL);
                var isCurrentStateStartAction = state.getMenuStep().equals(MenuStep.START_ACTION);

                var isCurrentStateMessageToGroup = state.getCurrentCommandType().equals(ActiveCommandType.MESSAGE_TO_GROUP);

                if(isCurrentStateMessageToAll && isCurrentStateStartAction) {
                    List<CompetitorModel> competitors = competitorRepository.findAll();
                    sendMessageToAll(state, update, competitors);
                }
                if(isCurrentStateMessageToGroup && isCurrentStateStartAction) {
                    var competitionType = state.getCompetitionType();
                    var competitors = competitorRepository.findByCompetitionType(competitionType).get();
                    sendMessageToAll(state, update, competitors);
                }
                else {
                    sendMessage(commandHandler.menuHandler(update));
                }
            }
        }
        if(update.hasMessage() && (update.getMessage().hasPhoto() || update.getMessage().hasDocument())) {
            try {
                handleFile(update.getMessage());
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
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

        if(chatIds.size() <1) {
            sendMessage.setText("В группе нет участников");
            execute(sendMessage);
        }
        else  {
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

    }

    private void sendMessageToAll(BotState state, Update update, List<CompetitorModel> competitors) {

        List<Long> chatIds = competitors.stream().map(CompetitorModel::getTelegramId).toList();

        var message = messageService.messageHandler(update);
        try {
            state.setMenuStep(MenuStep.DEFAULT);
            state.setCurrentCommandType(ActiveCommandType.DEFAULT);
            sendMessage(message, chatIds);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void removeMarkup(long chatId, int messageId) throws TelegramApiException {

        EditMessageReplyMarkup sendMessage = new EditMessageReplyMarkup();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setReplyMarkup(null);
        sendMessage.setMessageId((int)messageId);

        execute(sendMessage);
    }

    private void handleFile(Message message) throws TelegramApiException {
        long chatId = message.getChatId();
        String userGroup = "Undefined";
        String userName = message.getFrom().getUserName();
        var user = userRepository.findById(chatId).orElse(null);
        if(user != null) {
            userName = String.valueOf(user.getName());
        }
        String fileId = "";
        String fileName = "";

        if(message.hasDocument()) {
            fileId = message.getDocument().getFileId();
            fileName = message.getDocument().getFileName();
        }
        else {
            PhotoSize photo = message.getPhoto().get(message.getPhoto().size()-1);
            fileId = message.getPhoto().get(0).getFileId();
            fileName = "photo_" + message.getPhoto().get(0).getFileUniqueId() + ".jpg";
        }

        if (fileId == null || fileName == null) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(chatId));
            sendMessage.setText("Не удалось отправить файл");
            execute(sendMessage);
        }

        try {
            GetFile getFile = new GetFile();
            getFile.setFileId(fileId);
            File file = execute(getFile);

            java.io.File downloadedFile = downloadFile(file);

            var existingUser = competitorRepository.findById(chatId);
            if(existingUser.isPresent()) {
                userGroup = String.valueOf(existingUser.get().getCompetitionType());
                userName = String.valueOf(existingUser.get().getName());
            }
            Path directoryPath = Paths.get(saveDirectory, userGroup, userName);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            Path filePath = directoryPath.resolve(fileName);
            Files.copy(downloadedFile.toPath(), filePath, StandardCopyOption.REPLACE_EXISTING);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(chatId));
            sendMessage.setText(String.format("Файл %s успешно сохранен.", fileName));
            execute(sendMessage);
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(chatId));
            sendMessage.setText(String.format("Ошибка при сохранении файла: %s", e.getMessage()));
            execute(sendMessage);
        }
    }
}
