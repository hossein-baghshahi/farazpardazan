package com.farazpardazan.notificationsystem.provider.mci;

import com.farazpardazan.notificationsystem.provider.NotificationData;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Hossein Baghshahi
 */
public class MciNotificationData extends NotificationData {

    @JsonProperty("message")
    private String message;

    @JsonProperty("mobileNo")
    private String mobileNumber;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
