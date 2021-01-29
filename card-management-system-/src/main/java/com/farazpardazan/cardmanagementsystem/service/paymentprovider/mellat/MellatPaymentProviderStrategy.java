package com.farazpardazan.cardmanagementsystem.service.paymentprovider.mellat;

import com.farazpardazan.cardmanagementsystem.domain.Transfer;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentProviderException;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentProviderStrategy;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentResponse;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentStrategyName;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Hossein Baghshahi
 */
@Component
public class MellatPaymentProviderStrategy extends PaymentProviderStrategy {

    private final String URL = "https://first-payment-provider/payments/transfer";

    public MellatPaymentProviderStrategy(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public void moneyTransfer(Transfer transfer) throws PaymentProviderException {
        MellatPaymentDto mellatPaymentDto = new MellatPaymentDto(transfer.getSourceCard(), transfer.getDestinationCard(),
                transfer.getCvv(), transfer.getExpirationDate(), transfer.getPin(), transfer.getAmount().toString());
        ResponseEntity<PaymentResponse> response;

        try {
            response = restTemplate.postForEntity(URL, mellatPaymentDto, PaymentResponse.class);
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
        return PaymentStrategyName.MELLAT;
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
