package com.farazpardazan.notificationsystem.listener;

import com.farazpardazan.notificationsystem.provider.NotificationProvider;
import com.farazpardazan.notificationsystem.provider.mci.MciNotificationData;
import com.farazpardazan.notificationsystem.provider.mci.NotificationProviderException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Hossein Baghshahi
 */
@Component
public class NotificationMessageListener {

    private final NotificationProvider notificationProvider;

    public NotificationMessageListener(NotificationProvider notificationProvider) {
        this.notificationProvider = notificationProvider;
    }

    @KafkaListener(topics = "farazpardaz-sms-notification" , groupId = "sms")
    void listener(NotificationMessage notificationMessage){

        MciNotificationData mciNotificationData = new MciNotificationData();
        mciNotificationData.setMessage(notificationMessage.getMessage());
        mciNotificationData.setMobileNumber(notificationMessage.getMobileNumber());

        try {
            notificationProvider.sendNotification(mciNotificationData);
        } catch (NotificationProviderException e) {
            //todo retry
            e.printStackTrace();
        }
    }

}
