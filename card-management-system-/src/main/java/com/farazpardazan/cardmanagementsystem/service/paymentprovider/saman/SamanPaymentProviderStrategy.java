package com.farazpardazan.cardmanagementsystem.service.paymentprovider.saman;

import com.farazpardazan.cardmanagementsystem.domain.Transfer;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentProviderException;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentProviderStrategy;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentStrategyName;
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
public class SamanPaymentProviderStrategy implements PaymentProviderStrategy {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final String URL = "https://second-payment-provider/cards/pay";

    public SamanPaymentProviderStrategy(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        this.restTemplate = new RestTemplateBuilder()
                .messageConverters(new MappingJackson2HttpMessageConverter(this.objectMapper))
                .build();
    }

    @Override
    public void moneyTransfer(Transfer transfer) throws PaymentProviderException {
        SamanPaymentDto samanPaymentDto = new SamanPaymentDto(transfer.getSourceCard(), transfer.getDestinationCard(),
                transfer.getCvv(), transfer.getExpirationDate(), transfer.getPin(), transfer.getAmount().toString());

        ResponseEntity<SamanPaymentResponse> response;

        try {
            response = restTemplate.postForEntity(URL, samanPaymentDto, SamanPaymentResponse.class);
        } catch (Exception ex) {
            throw new PaymentProviderException(ex.getMessage());
        }

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null)
            throw new PaymentProviderException("Error in money transfer operation.");

        if (!response.getBody().isSuccessful())
            throw new PaymentProviderException("Money transfer failed.");

    }

    @Override
    public PaymentStrategyName getPaymentStrategyName() {
        return PaymentStrategyName.SAMAN;
    }

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
