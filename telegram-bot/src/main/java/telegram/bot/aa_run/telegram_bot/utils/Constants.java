package telegram.bot.aa_run.telegram_bot.utils;

import jakarta.servlet.http.PushBuilder;

public abstract class Constants {

    public static final String BOT_NAME = "JavaSpringBot";
    public static final String BOT_DESCRIPTION = "Bot for Java Spring Bot";
    public static final String BOT_VERSION = "1.0.0";

    public static final String UNKNOWN_COMMAND = "Команда не распознана. Попробуйте снова.";

    public static final String REGISTRATION_PERIOD_IS_ENDED_MESSAGE = "Время регистрации прошло.";
    public static final String REGISTRATION_PERIOD_IS_NOT_STARTED_MESSAGE = "Регистрация еще не началась.";
    public static final String DISALLOW_ADD_RESULT_EVENT_NOT_STARTED_MESSAGE = "Челлендж ещё не начался, результаты добавлять нельзя.";
    public static final String DISALLOW_ADD_RESULT_EVENT_IS_ENDED_MESSAGE = "Челлендж завершён, результаты добавлять нельзя.";
    public static final String STATISTICS_IS_UNAVAILABLE_MESSAGE = "Просмотр статистики недоступен";
    public static final String COUNTING_RESULTS_MESSAGE = "Ведётся проверка результатов.";
    public static final String ERROR_OCCURRED_MESSAGE = "Произошла ошибка.";

    public static final String COMPETITION_TYPE01_NAME = "872000 шагов";
    public static final String COMPETITION_TYPE02_NAME = "403000 шагов";
    public static final String COMPETITION_TYPE03_NAME = "250000 шагов";
    public static final String COMPETITION_TYPE04_NAME = "Велосипед 50 км";
    public static final String COMPETITION_TYPE05_NAME = "Бег 17 км";

    public static final String COMPETITION_TYPE01_DESCRIPTION = "Пройти 872000 шагов";
    public static final String COMPETITION_TYPE02_DESCRIPTION = "Пройти 403000 шагов";
    public static final String COMPETITION_TYPE03_DESCRIPTION = "Пройти 250000 шагов";
    public static final String COMPETITION_TYPE04_DESCRIPTION = "Проехать на велосипеде 50 км";
    public static final String COMPETITION_TYPE05_DESCRIPTION = "Пробежать 17 километров";

    public static final String COMPETITION_TYPE01_TOTAL_PLACES = "100";
    public static final String COMPETITION_TYPE02_TOTAL_PLACES = "100";
    public static final String COMPETITION_TYPE03_TOTAL_PLACES = "100";
    public static final String COMPETITION_TYPE04_TOTAL_PLACES = "100";
    public static final String COMPETITION_TYPE05_TOTAL_PLACES = "100";


}
