package telegram.bot.aa_run.telegram_bot.models;

import telegram.bot.aa_run.telegram_bot.models.enums.CallbackType;

public class CallbackModel {

    private CallbackType callbackType;
    private String data;

    public CallbackModel() {}
    public CallbackModel(CallbackType callbackType, String data) {
        this.callbackType = callbackType;
        this.data = data;
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
