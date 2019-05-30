package ru.test.avito.DTO;

public class CallbackAdvertData {
    private String action;
    private Long advertId;

    CallbackAdvertData() {

    }

    public CallbackAdvertData(String action, Long advertId) {
        this.action = action;
        this.advertId = advertId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getAdvertId() {
        return advertId;
    }

    public void setAdvertId(Long advertId) {
        this.advertId = advertId;
    }
}
