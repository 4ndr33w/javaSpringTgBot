package telegram.bot.aa_run.telegram_bot.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import telegram.bot.aa_run.telegram_bot.models.BotState;
import telegram.bot.aa_run.telegram_bot.telegram.TelegramBot;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class BotInit {

    private final TelegramBot telegramBot;
    private final CommandProperties commandProperties;

    @Autowired
    public BotInit(TelegramBot telegramBot, CommandProperties commandProperties) {

        this.telegramBot = telegramBot;
        this.commandProperties = commandProperties;
        //initBotState();
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(telegramBot);
            telegramBot.execute(commandProperties.setMyCommands);
        } catch (TelegramApiException e) {
            //log.error(e.getMessage());
        }
    }
}
