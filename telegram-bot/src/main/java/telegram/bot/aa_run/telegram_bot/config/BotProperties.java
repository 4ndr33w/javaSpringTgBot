package telegram.bot.aa_run.telegram_bot.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "bot")
@PropertySource("classpath:application.yaml")
//@RequiredArgsConstructor
public class BotProperties {

    private String token;
    private String name;
    private String admin;

    private DefaultBotOptions botOptions;

    private Map<String, BotProperties.CommandConfig> commands;
    private Map<String, String> messages;
    private Map<String, List<List<String>>> keyboard;

    public BotProperties() {}
    public BotProperties(String token, String name, String admin, Map<String, CommandConfig> commands, Map<String, String> messages, Map<String, List<List<String>>> keyboard) {
        this.token = token;
        this.name = name;
        this.admin = admin;
        this.commands = commands;
        this.messages = messages;
        this.keyboard = keyboard;
        botOptions = new DefaultBotOptions();
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

    public DefaultBotOptions getBotOptions() {return botOptions;}
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAdmin() {
        return admin;
    }
    public void setAdmin(String admin) {
        this.admin = admin;
    }
    public Map<String, CommandConfig> getCommands() {
        return commands;
    }
    public void setCommands(Map<String, CommandConfig> commands) {
        this.commands = commands;
    }
    public Map<String, String> getMessages() {
        return messages;
    }
    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }
    public Map<String, List<List<String>>> getKeyboard() {
        return keyboard;
    }
    public void setKeyboard(Map<String, List<List<String>>> keyboard) {
        this.keyboard = keyboard;
    }
}
