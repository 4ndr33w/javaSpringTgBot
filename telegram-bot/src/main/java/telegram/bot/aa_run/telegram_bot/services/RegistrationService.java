package telegram.bot.aa_run.telegram_bot.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import telegram.bot.aa_run.telegram_bot.models.BotState;
import telegram.bot.aa_run.telegram_bot.utils.UtilClass;
import telegram.bot.aa_run.telegram_bot.utils.MarkupHandler;
import telegram.bot.aa_run.telegram_bot.models.enums.MenuStep;
import telegram.bot.aa_run.telegram_bot.models.CompetitorModel;
import telegram.bot.aa_run.telegram_bot.models.enums.CompetitionType;
import telegram.bot.aa_run.telegram_bot.models.companies.CompanyModel;
import telegram.bot.aa_run.telegram_bot.models.abstractions.ModelBase;
import telegram.bot.aa_run.telegram_bot.models.enums.ActiveCommandType;
import telegram.bot.aa_run.telegram_bot.repositories.CompanyRepository;
import telegram.bot.aa_run.telegram_bot.telegram.handlers.CommandHandler;
import telegram.bot.aa_run.telegram_bot.repositories.CompaniesRepository;
import telegram.bot.aa_run.telegram_bot.repositories.CompetitorRepository;

@Service
public class RegistrationService {

    private final CompanyRepository companyRepository;
    private final CompaniesRepository companiesRepository;
    private final CompetitorRepository competitorRepository;

    public RegistrationService(CompaniesRepository companiesRepository, CompanyRepository companyRepository, CompetitorRepository competitorRepository) {
        this.companyRepository = companyRepository;
        this.companiesRepository = companiesRepository;
        this.competitorRepository = competitorRepository;
    }
    public SendMessage startRegistrationProcess(Update update) {

        long chatId = update.getMessage().getChatId() == null? update.getCallbackQuery().getFrom().getId() : update.getMessage().getChatId();
        var state = CommandHandler.botState.get(chatId);

        var existingCompetitor = competitorRepository.findById(chatId);
        if(existingCompetitor.isPresent()) {

            state.setCurrentCommandType(ActiveCommandType.DEFAULT);
            state.setRegistrationStep(MenuStep.DEFAULT);
            String name = existingCompetitor.get().getName();
            String competitionType = UtilClass.getEventTypeDescription(existingCompetitor.get().getCompetitionType());
            return new SendMessage(String.valueOf(chatId), String.format("%s, Вы уже зарегистрированы. \nВы выбрали участие в событии: \n`%s`", name, competitionType));
        }
        state.setRegistrationStep(MenuStep.NAME);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("Введите фамилию и имя");
        return sendMessage;
    }

    public SendMessage registrationHandler(Update update) {

        long chatId = getChatId(update);
        SendMessage message = new SendMessage();

        message.setChatId(String.valueOf(chatId));
        var state = CommandHandler.botState.get(chatId);
        switch (state.getRegistrationStep()) {
            case NAME -> {
                return setCompetitorName(message, update, state);
            }
            case AGE -> {
                return setCompetitorAge(message, update, state);
            }
            case GENDER -> {
                return setCompetitorGender(message, update, state);
            }
            case REGION -> {
                return setCompetitorRegion(message, update, state);
            }
            case CITY -> {
                return setCompetitorCity(message, update, state);
            }
            case COMPANY -> {
                return setCompetitorCompany(message, update, state);
            }
            case COMPETITION_TYPE -> {
                return finishRegistration(message, update, state);
            }
        }
        return null;
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

    private SendMessage setCompetitorName(SendMessage message, Update update, BotState state) {
        var user = new CompetitorModel();
        long chatId = update.getMessage().getChatId();
        user.setTelegramId(chatId);
        user.setName(update.getMessage().getText());

        state.setCompetitorModel(user);
        state.setRegistrationStep(MenuStep.AGE);

        message.setChatId(String.valueOf(chatId));
        message.setText("Введите возраст");
        return message;
    }

    private SendMessage setCompetitorAge(SendMessage message, Update update, BotState state) {
        state.getCompetitorModel().setAge(Integer.parseInt(update.getMessage().getText()));
        state.setRegistrationStep(MenuStep.GENDER);

        String userName = state.getCompetitorModel().getName();
        message.setText(String.format("%s, укажите ваш пол", userName));
        message.setReplyMarkup(MarkupHandler.genderMarkup());
        return message;
    }

    private SendMessage setCompetitorGender(SendMessage message, Update update, BotState state) {
        state.getCompetitorModel().setGender(update.getCallbackQuery().getData().charAt(0));
        state.setRegistrationStep(MenuStep.REGION);

        var regionsOptional = companiesRepository.findAllRegions();

        if (regionsOptional.isPresent()) {
            List<ModelBase> regions = new ArrayList<>(regionsOptional.get());
            var markup = MarkupHandler.collectionMarkup(regions);
            message.setReplyMarkup(markup);
        }
        message.setText(String.format("%s, выберите регион, в котором находится ваше предприятие", state.getCompetitorModel().getName()));

        return message;
    }

    private SendMessage setCompetitorRegion(SendMessage message, Update update, BotState state) {
        int regionId = Integer.parseInt(update.getCallbackQuery().getData());
        state.setRegionId(regionId);
        state.setRegistrationStep(MenuStep.CITY);

        var citiesOptional = companiesRepository.findAllCities(regionId);

        if (citiesOptional.isPresent()) {
            List<ModelBase> cities = new ArrayList<>(citiesOptional.get());
            var markup = MarkupHandler.collectionMarkup(cities);
            message.setReplyMarkup(markup);
        }

        message.setText(String.format("%s, выберите город, в котором находится ваше предприятие", state.getCompetitorModel().getName()));
        return message;
    }

    private SendMessage setCompetitorCity(SendMessage message, Update update, BotState state) {
        int cityId = Integer.parseInt(update.getCallbackQuery().getData());
        state.setCityId(cityId);
        state.setRegistrationStep(MenuStep.COMPANY);

        var companiesOptional = companiesRepository.findAllCompanies(cityId);
        if (companiesOptional.isPresent()) {
            List<ModelBase> companies = new ArrayList<>(companiesOptional.get());
            var markup = MarkupHandler.collectionMarkup(companies);
            message.setReplyMarkup(markup);
        }

        message.setText(String.format("%s, выберите ваше предприятие", state.getCompetitorModel().getName()));
        return message;
    }

    private SendMessage setCompetitorCompany(SendMessage message, Update update, BotState state) {
        int companyId = Integer.parseInt(update.getCallbackQuery().getData());
        Optional<CompanyModel> company = companyRepository.findById(companyId);

        company.ifPresent(u -> state.getCompetitorModel().setCompany(u.getName()));
        state.setRegistrationStep(MenuStep.COMPETITION_TYPE);

        var markup = MarkupHandler.eventTypeMarkup();
        message.setReplyMarkup(markup);
        message.setText(String.format("%s - %s, выберите событие, в котором желаете принять участие", state.getCompetitorModel().getName(), state.getCompetitorModel().getCompany()));
        return message;
    }

    private SendMessage finishRegistration(SendMessage message, Update update, BotState state) {
        CompetitionType competitionTypeEnum = Enum.valueOf(CompetitionType.class, update.getCallbackQuery().getData());
        String competitionType = UtilClass.getEventTypeDescription(competitionTypeEnum);
        state.getCompetitorModel().setCompetitionType(competitionTypeEnum);

        var competitor = state.getCompetitorModel();
        competitor.setCreatedAt(new Date());
        competitor.setUpdatedAt(new Date());
        competitorRepository.save(competitor);
        state.setRegistrationStep(MenuStep.DEFAULT);
        state.setCurrentCommandType(ActiveCommandType.DEFAULT);

        String name = competitor.getName();

        message.setText(String.format("%s, вы успешно зарегистрировались на участе в событии: %s", name, competitionType));
        return message;
    }
}