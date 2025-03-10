package telegram.bot.aa_run.telegram_bot.models.Dtos;

import lombok.*;

import telegram.bot.aa_run.telegram_bot.models.enums.CallbackType;

public class CallbackDto {

    private String data;
    private CallbackType callbackType;

    public CallbackDto() {}
    public CallbackDto(String data, CallbackType callbackType) {
        this.data = data;
        this.callbackType = callbackType;
    }

    public CallbackType getCallbackType() {
        return callbackType;
    }
    public void setCallbackType(CallbackType callbackType) {
        this.callbackType = callbackType;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
}
