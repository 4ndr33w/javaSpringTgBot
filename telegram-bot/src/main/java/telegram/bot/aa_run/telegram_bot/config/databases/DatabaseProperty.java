package telegram.bot.aa_run.telegram_bot.config.databases;

public class DatabaseProperty {

    private static String url;
    private static String username;
    private static String password;
    private static String driverClassName;

    public DatabaseProperty(String url, String username, String password, String driverClassName) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
    }

    public static String getUrl() {
        return url;
    }
    public static void setUrl(String url) {
        DatabaseProperty.url = url;
    }
    public static String getUsername() {
        return username;
    }
    public static void setUsername(String username) {
        DatabaseProperty.username = username;
    }
    public static String getPassword() {
        return password;
    }
    public static void setPassword(String password) {
        DatabaseProperty.password = password;
    }
    public static String getDriverClassName() {
        return driverClassName;
    }
    public static void setDriverClassName(String driverClassName) {
        DatabaseProperty.driverClassName = driverClassName;
    }
}
