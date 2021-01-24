package com.farazpardazan.cardmanagementsystem.service.notification;

import com.farazpardazan.cardmanagementsystem.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Hossein Baghshahi
 */
@Service
public class SmsNotificationService implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(SmsNotificationService.class);

    private final KafkaTemplate<String, NotificationMessage> kafkaTemplate;

    public SmsNotificationService(KafkaTemplate<String, NotificationMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendNotification(String message, User receiverUser) {
        try {
            NotificationMessage notificationMessage =
                    new NotificationMessage(receiverUser.getMobileNumber(), message);
            kafkaTemplate.send("farazpardaz-sms-notification", notificationMessage);
        } catch (Exception exception) {
            logger.error("Error sending transfer notification", exception);
        }
    }
}
