package com.farazpardazan.cardmanagementsystem.service.notification;

import com.farazpardazan.cardmanagementsystem.domain.User;

/**
 * @author Hossein Baghshahi
 */
public interface NotificationService {

    void sendNotification(String message, User receiverUser);
}
