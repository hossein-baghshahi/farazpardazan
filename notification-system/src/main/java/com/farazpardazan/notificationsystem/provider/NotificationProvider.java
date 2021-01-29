package com.farazpardazan.notificationsystem.provider;

import com.farazpardazan.notificationsystem.provider.mci.NotificationProviderException;

/**
 * @author Hossein Baghshahi
 */
public interface  NotificationProvider {

    void sendNotification(NotificationData notificationData) throws NotificationProviderException;

}
