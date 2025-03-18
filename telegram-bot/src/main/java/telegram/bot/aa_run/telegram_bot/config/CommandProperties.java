package telegram.bot.aa_run.telegram_bot.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "bot.commands")
public class CommandProperties {

    List<BotCommand> menuCommandList = new ArrayList<BotCommand> () {
        {
            add(new BotCommand("/start", "Начало работы бота"));
            add(new BotCommand("/help", "Помощь"));
            add(new BotCommand("/register", "Регистрация"));
            add(new BotCommand("/reply", "Связаться с администратором бота"));
        }
    };
    public SetMyCommands setMyCommands = SetMyCommands.builder().commands(menuCommandList).build();
}