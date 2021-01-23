package com.farazpardazan.cardmanagementsystem.service.paymentprovider.mellat;

import com.farazpardazan.cardmanagementsystem.domain.Transfer;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentProviderException;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentStrategyName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * @author Hossein Baghshahi
 */
@ExtendWith(SpringExtension.class)
class MellatPaymentProviderStrategyTest {

    private MellatPaymentProviderStrategy mellatPaymentProviderStrategy;

    private ObjectMapper objectMapper;

    /**
     * Mock Rest Service Server.
     */
    private MockRestServiceServer mockRestServiceServer;

    private String url;

    private Transfer dummyTransfer;

    @BeforeEach
    public void setup(){
        objectMapper = new ObjectMapper();
        mellatPaymentProviderStrategy = new MellatPaymentProviderStrategy(objectMapper);
        mockRestServiceServer = MockRestServiceServer.createServer(mellatPaymentProviderStrategy.getRestTemplate());

        url = "https://first-payment-provider/payments/transfer";

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
                .isThrownBy(() -> mellatPaymentProviderStrategy.moneyTransfer(dummyTransfer));
    }

    @Test
    void moneyTransfer_WithFailedResponse_ShouldThrowException() {
        String dummyResponseBody = "{\"successful\": \"false\"}";
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(url))
                .andExpect(method(HttpMethod.POST)).andRespond(withSuccess()
                .contentType(MediaType.APPLICATION_JSON).body(dummyResponseBody));

        assertThatExceptionOfType(PaymentProviderException.class)
                .isThrownBy(() -> mellatPaymentProviderStrategy.moneyTransfer(dummyTransfer));
    }

    @Test
    void moneyTransfer_WithoutSuccessfulResponse_ShouldNotThrowAnyException() {
        String dummyResponseBody = "{\"successful\": \"true\"}";
        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(url))
                .andExpect(method(HttpMethod.POST)).andRespond(withSuccess()
                .contentType(MediaType.APPLICATION_JSON).body(dummyResponseBody));

        assertThatNoException().isThrownBy(() -> mellatPaymentProviderStrategy.moneyTransfer(dummyTransfer));
    }


    @Test
    void getPaymentStrategyName_ShouldReturnMellatPaymentStrategyName() {
        PaymentStrategyName paymentStrategyName = mellatPaymentProviderStrategy.getPaymentStrategyName();
        assertThat(paymentStrategyName).isEqualTo(PaymentStrategyName.MELLAT);
    }
}