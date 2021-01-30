package com.farazpardazan.cardmanagementsystem.service.paymentprovider.saman;

import com.farazpardazan.cardmanagementsystem.domain.Transfer;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentProviderException;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentProviderStrategy;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentResponse;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentStrategyName;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author Hossein Baghshahi
 */
@Component
public class SamanPaymentProviderStrategy extends PaymentProviderStrategy {

    private final String URL = "https://second-payment-provider/cards/pay";

    public SamanPaymentProviderStrategy(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    @CircuitBreaker(name = "samanPaymentProvider", fallbackMethod = "fallback")
    public Transfer.Status moneyTransfer(Transfer transfer) throws PaymentProviderException {
        SamanPaymentDto samanPaymentDto = new SamanPaymentDto(transfer.getSourceCard(), transfer.getDestinationCard(),
                transfer.getCvv(), transfer.getExpirationDate(), transfer.getPin(), transfer.getAmount().toString());

        ResponseEntity<PaymentResponse> response;

        try {
            response = restTemplate.postForEntity(URL, samanPaymentDto, PaymentResponse.class);
        } catch (Exception ex) {
            throw new PaymentProviderException(ex.getMessage());
        }

        if (response.getStatusCode().isError() || response.getBody() == null)
            throw new PaymentProviderException("Error in money transfer operation.");

        if (response.getBody().isSuccessful()) {
            return Transfer.Status.SUCCESSFUL;
        }

        return Transfer.Status.FAILED;

    }

    @Override
    public PaymentStrategyName getPaymentStrategyName() {
        return PaymentStrategyName.SAMAN;
    }

}
