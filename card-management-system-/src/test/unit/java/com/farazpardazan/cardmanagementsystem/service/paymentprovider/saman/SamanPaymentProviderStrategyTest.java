package com.farazpardazan.cardmanagementsystem.service.paymentprovider.saman;

import com.farazpardazan.cardmanagementsystem.domain.Transfer;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentProviderException;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentStrategyName;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * @author Hossein Baghshahi
 */
@ExtendWith(SpringExtension.class)
class SamanPaymentProviderStrategyTest {

    private SamanPaymentProviderStrategy samanPaymentProviderStrategy;

    private ObjectMapper objectMapper;

    /**
     * Mock Rest Service Server.
     */
    private MockRestServiceServer mockRestServiceServer;

    private String url;

    private Transfer dummyTransfer;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        samanPaymentProviderStrategy = new SamanPaymentProviderStrategy(objectMapper);
        mockRestServiceServer = MockRestServiceServer.createServer(samanPaymentProviderStrategy.getRestTemplate());

        url = "https://second-payment-provider/cards/pay";

        dummyTransfer = new Transfer();
        dummyTransfer.setSourceCard("6219861034561234");
        dummyTransfer.setDestinationCard("6219861034568754");
        dummyTransfer.setStatus(Transfer.Status.PENDING);
        dummyTransfer.setAmount(1000000L);
        dummyTransfer.setCreatedDate(LocalDateTime.now());
    }

    @Test
    void moneyTransfer_WithProviderServerApiError_ShouldThrowPaymentProviderException() {
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(url))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withServerError().contentType(MediaType.APPLICATION_JSON));

        assertThatExceptionOfType(PaymentProviderException.class)
                .isThrownBy(() -> samanPaymentProviderStrategy.moneyTransfer(dummyTransfer));
    }

    @Test
    void moneyTransfer_WithFailedResponse_ShouldThrowException() throws PaymentProviderException {
        String dummyResponseBody = "{\"successful\": \"false\"}";
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(url))
                .andExpect(method(HttpMethod.POST)).andRespond(withSuccess()
                .contentType(MediaType.APPLICATION_JSON).body(dummyResponseBody));

        Transfer.Status transferStatus = samanPaymentProviderStrategy.moneyTransfer(dummyTransfer);

        assertThat(transferStatus).isEqualTo(Transfer.Status.FAILED);
    }

    @Test
    void moneyTransfer_WithoutSuccessfulResponse_ShouldReturnSuccessful() throws PaymentProviderException {
        String dummyResponseBody = "{\"successful\": \"true\"}";
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(url))
                .andExpect(method(HttpMethod.POST)).andRespond(withSuccess()
                .contentType(MediaType.APPLICATION_JSON).body(dummyResponseBody));

        Transfer.Status transferStatus = samanPaymentProviderStrategy.moneyTransfer(dummyTransfer);

        assertThat(transferStatus).isEqualTo(Transfer.Status.SUCCESSFUL);
    }

    @Test
    void getPaymentStrategyName_ShouldReturnMellatPaymentStrategyName() {
        PaymentStrategyName paymentStrategyName = samanPaymentProviderStrategy.getPaymentStrategyName();
        assertThat(paymentStrategyName).isEqualTo(PaymentStrategyName.SAMAN);
    }
}