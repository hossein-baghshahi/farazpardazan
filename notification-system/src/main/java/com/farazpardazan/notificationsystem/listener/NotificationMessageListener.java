package com.farazpardazan.notificationsystem.listener;

import com.farazpardazan.notificationsystem.provider.NotificationProvider;
import com.farazpardazan.notificationsystem.provider.NotificationProviderException;
import com.farazpardazan.notificationsystem.provider.mci.MciNotificationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Hossein Baghshahi
 */
@Component
public class NotificationMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationMessageListener.class);

    private final NotificationProvider notificationProvider;

    public NotificationMessageListener(NotificationProvider notificationProvider) {
        this.notificationProvider = notificationProvider;
    }

    @KafkaListener(topics = "farazpardaz-sms-notification", groupId = "sms")
    void listener(NotificationMessage notificationMessage) {
        MciNotificationData mciNotificationData = new MciNotificationData();
        mciNotificationData.setMessage(notificationMessage.getMessage());
        mciNotificationData.setMobileNumber(notificationMessage.getMobileNumber());

        try {
            notificationProvider.sendNotification(mciNotificationData);
        } catch (NotificationProviderException e) {
            logger.error(e.getMessage());
        }
    }

}
