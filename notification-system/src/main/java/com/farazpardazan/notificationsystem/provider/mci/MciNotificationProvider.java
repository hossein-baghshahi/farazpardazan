package com.farazpardazan.notificationsystem.provider.mci;

import com.farazpardazan.notificationsystem.provider.NotificationData;
import com.farazpardazan.notificationsystem.provider.NotificationProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Hossein Baghshahi
 */
@Component
public class MciNotificationProvider implements NotificationProvider {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final String URL = " https://sms-provider/messages/send-sms";

    public MciNotificationProvider(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        this.restTemplate = new RestTemplateBuilder()
                .messageConverters(new MappingJackson2HttpMessageConverter(this.objectMapper))
                .build();
    }

    @Override
    public void sendNotification(NotificationData notificationData) throws NotificationProviderException {

        ResponseEntity<MciNotificationResponse> response;

        try {
            response = restTemplate.postForEntity(URL, notificationData, MciNotificationResponse.class);
        } catch (Exception ex) {
            throw new NotificationProviderException(ex.getMessage());
        }

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null)
            throw new NotificationProviderException("Error in sending notification.");

        if (!response.getBody().isSuccessful())
            throw new NotificationProviderException("Sending notification failed.");


    }
}
