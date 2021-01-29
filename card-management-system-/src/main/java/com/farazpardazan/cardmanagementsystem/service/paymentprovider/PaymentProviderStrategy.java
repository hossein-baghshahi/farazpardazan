package com.farazpardazan.cardmanagementsystem.service.paymentprovider;

import com.farazpardazan.cardmanagementsystem.domain.Transfer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * @author Hossein Baghshahi
 */
public abstract class PaymentProviderStrategy {

    protected final RestTemplate restTemplate;

    protected final ObjectMapper objectMapper;

    public PaymentProviderStrategy(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        this.restTemplate = new RestTemplateBuilder()
                .messageConverters(new MappingJackson2HttpMessageConverter(this.objectMapper))
                .build();
    }


    public abstract void moneyTransfer(Transfer transfer) throws PaymentProviderException;

    public abstract PaymentStrategyName getPaymentStrategyName();
}
