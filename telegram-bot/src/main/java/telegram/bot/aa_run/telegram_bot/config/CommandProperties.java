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


    private static final Map<String, CommandConfig> commands = new HashMap<String, CommandConfig>() {
        {
            put("/start", new CommandConfig("CommandProperties", "Bot Greetings Message from CommandProperties"));
            put("/help", new CommandConfig("Помощь!!", "Список доступных команд"));
            put("/register", new CommandConfig("Регистрация", "Введите ваши имя и вамилию:"));
        }
    };

    List<BotCommand> menuCommandList = new ArrayList<BotCommand> () {
        {
            add(new BotCommand("/start", "Начало работы бота"));
            add(new BotCommand("/help", "Помощь"));
            add(new BotCommand("/register", "Регистрация"));
            add(new BotCommand("/reply", "Связаться с администратором бота"));
        }
    };
    public SetMyCommands setMyCommands = SetMyCommands.builder().commands(menuCommandList).build();

    public static Map<String, CommandConfig> getCommands() {
        return commands;
    }
    public static class CommandConfig {
        private String description;
        private String responseMessage;

        public CommandConfig(String s, String s1) {
            this.description = s;
            this.responseMessage = s1;
        }

        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public String getResponseMessage() {
            return responseMessage;
        }
        public void setResponseMessage(String responseMessage) {
            this.responseMessage = responseMessage;
        }
    }
}