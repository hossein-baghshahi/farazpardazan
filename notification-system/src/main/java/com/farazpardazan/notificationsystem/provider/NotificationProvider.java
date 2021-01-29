package com.farazpardazan.notificationsystem.provider;

/**
 * @author Hossein Baghshahi
 */
public interface NotificationProvider {

    void sendNotification(NotificationData notificationData) throws NotificationProviderException;

}
