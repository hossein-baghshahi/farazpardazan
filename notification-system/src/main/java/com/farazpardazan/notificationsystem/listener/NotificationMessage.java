package com.farazpardazan.notificationsystem.listener;

/**
 * @author Hossein Baghshahi
 */
public class NotificationMessage {
    private String mobileNumber;

    private String message;

    public NotificationMessage() {
    }

    public NotificationMessage(String mobileNumber, String message) {
        this.mobileNumber = mobileNumber;
        this.message = message;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "NotificationMessage{" +
                "mobileNumber='" + mobileNumber + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
