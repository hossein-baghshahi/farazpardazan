package com.farazpardazan.cardmanagementsystem.service.paymentprovider;

import com.farazpardazan.cardmanagementsystem.domain.Transfer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

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
                .setConnectTimeout(Duration.ofMillis(1000))
                .build();
    }

    public abstract Transfer.Status moneyTransfer(Transfer transfer) throws PaymentProviderException;

    public abstract PaymentStrategyName getPaymentStrategyName();

    /**
     * In fallback of calling payment provider API we can have three scenario:
     * 1- Return PENDING status for transfer and later call API again.
     * 2- Tell user that service is unavailable right now.
     * 3- Choose another payment provider.
     */
    protected Transfer.Status fallback(Exception ex) {
        return Transfer.Status.PENDING;
    }

    ;

    /**
     * Exposes the rest template to be used in tests.
     * This is not provided in the interface.
     *
     * @return Current {@linkplain #restTemplate}.
     */
    public RestTemplate getRestTemplate() {
        return this.restTemplate;
    }
}
