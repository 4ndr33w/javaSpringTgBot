package telegram.bot.aa_run.telegram_bot.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.bot.aa_run.telegram_bot.models.BotState;
import telegram.bot.aa_run.telegram_bot.models.enums.ActiveCommandType;
import telegram.bot.aa_run.telegram_bot.models.enums.CompetitionType;
import telegram.bot.aa_run.telegram_bot.models.enums.MenuStep;
import telegram.bot.aa_run.telegram_bot.models.postgre.CompetitorModel;
import telegram.bot.aa_run.telegram_bot.repositories.postgre.CompetitorRepository;
import telegram.bot.aa_run.telegram_bot.telegram.handlers.CommandHandler;
import telegram.bot.aa_run.telegram_bot.utils.MarkupHandler;

import java.util.List;

@Service
public class MessageService {

    private final CompetitorRepository competitorRepository;

    public MessageService(CompetitorRepository competitorRepository) {
        this.competitorRepository = competitorRepository;
    }
    public SendMessage getMessage(String chatId, String message) {

        return new SendMessage();
    }

    public SendMessage messageHandler(Update update) {
        long chatId = getChatId(update);
        BotState state = CommandHandler.botState.get(chatId);

        return switch (state.getMenuStep()) {
            case DEFAULT -> {
                String message = getMessageText(state);
                yield confirmActionRequest(update, message);
            }
            case CONFIRM_ACTION -> requestActionCallbackQuery(update);
            case REQUEST_ACTION -> {
                String message = getMessageText(state);
                yield continueAction(update, message);
            }
            case REQUEST_GROUP_ACTION -> groupSelectAction(update);
            case START_ACTION -> startAction(update);
            default -> null;
        };
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

    public SendMessage confirmActionRequest(Update update, String requestMessage) {
        long chatId = update.getMessage().getChatId();
        BotState state = CommandHandler.botState.get(chatId);

        state.setMenuStep(MenuStep.CONFIRM_ACTION);

        SendMessage  sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(requestMessage);
        sendMessage.setReplyMarkup(MarkupHandler.getContinueOrCancelMarkup());

        return sendMessage;
    }

    public SendMessage requestActionCallbackQuery(Update update) {
        var callback = update.getCallbackQuery().getData();
        long chatId = getChatId(update);
        if (callback.equals("continue")) {
            BotState state = CommandHandler.botState.get(chatId);
            state.setMenuStep(MenuStep.REQUEST_ACTION);
            return continueAction(update, getMessageText(state));
        }
        else  {
            return cancelAction(chatId);
        }
    }

    private SendMessage continueAction(Update update, String requestMessage) {
        long chatId = getChatId(update);
        BotState state = CommandHandler.botState.get(chatId);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        if(state.getCurrentCommandType().equals(ActiveCommandType.MESSAGE_TO_ALL)) {
            state.setMenuStep(MenuStep.START_ACTION);
            sendMessage.setText(requestMessage);
        }

        if(state.getCurrentCommandType().equals(ActiveCommandType.MESSAGE_TO_GROUP)) {
            state.setMenuStep(MenuStep.REQUEST_GROUP_ACTION);
            sendMessage.setText(requestMessage);
            sendMessage.setReplyMarkup(MarkupHandler.getEventTypeMarkup());
        }
        return sendMessage;
    }

    private SendMessage groupSelectAction(Update update) {
        long chatId = getChatId(update);
        BotState state = CommandHandler.botState.get(chatId);
        var callback = update.getCallbackQuery().getData();

        CompetitionType eventType = Enum.valueOf(CompetitionType.class, callback);
        state.setCompetitionType(eventType);
        state.setMenuStep(MenuStep.START_ACTION);

        var competitors = competitorRepository.findByCompetitionType(eventType);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        if(competitors.get().isEmpty()) {
            sendMessage.setText("В группе нет участников");
            state.setMenuStep(MenuStep.DEFAULT);
            state.setCurrentCommandType(ActiveCommandType.DEFAULT);
        }
        else  {
            sendMessage.setText("Введите сообщение");
        }
        return sendMessage;
    }

    public SendMessage startAction(Update update) {
        SendMessage sendMessage = new SendMessage();
        String message = update.getMessage().getText();
        sendMessage.setText(message);
        sendMessage.setChatId(String.valueOf(getChatId(update)));
        return sendMessage;
    }

    private SendMessage cancelAction(long chatId) {

        var state = CommandHandler.botState.get(chatId);
        state.setMenuStep(MenuStep.DEFAULT);
        state.setCurrentCommandType(ActiveCommandType.DEFAULT);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("Отмена");
        return sendMessage;
    }

    private String getMessageText(BotState state) {

        if(state.getMenuStep().equals(MenuStep.CONFIRM_ACTION)) {
            return "Введите сообщение";
        }
        if(state.getMenuStep().equals(MenuStep.REQUEST_GROUP_ACTION)) {
            return "Выберите группу";
        }
        else {
            switch (state.getCurrentCommandType()) {
                case MESSAGE_TO_ALL: {

                    if(state.getMenuStep().equals(MenuStep.REQUEST_ACTION)) {
                        return "Введите сообщение";
                    }
                    if(state.getMenuStep().equals(MenuStep.CONFIRM_ACTION)) {

                    }
                    return "Отправить сообщение всем участникам.\nПродолжить?";
                }
                case MESSAGE_TO_GROUP:{
                    return "Отправить сообщение группе участников.\nПродолжить?";
                }
                default:{
                    return "";
                }
            }
        }
    }

    private List<CompetitorModel> getCompetitors(BotState state) {

        if(state.getCurrentCommandType().equals(ActiveCommandType.MESSAGE_TO_ALL)) {
            return competitorRepository.findAll();
        }
        else {
            return null;
        }
    }
}
