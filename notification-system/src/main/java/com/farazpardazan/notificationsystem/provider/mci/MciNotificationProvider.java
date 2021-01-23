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
    public void sendNotification(NotificationData notificationData) {

        /*ResponseEntity<MellatPaymentResponse> response;

        try {
            response = restTemplate.getForEntity(URL, MellatPaymentResponse.class);
        } catch (Exception ex) {
            throw new PaymentProviderException(ex.getMessage());
        }


        if (!response.getStatusCode().is2xxSuccessful())
            throw new PaymentProviderException("Error in money transfer operation.");

        if (!response.getBody().isSuccessful())
            throw new PaymentProviderException("Money transfer failed.");*/


    }
}
