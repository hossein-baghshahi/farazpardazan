package com.farazpardazan.cardmanagementsystem.service.transfer;

import com.farazpardazan.cardmanagementsystem.domain.Card;
import com.farazpardazan.cardmanagementsystem.domain.Transfer;
import com.farazpardazan.cardmanagementsystem.domain.User;
import com.farazpardazan.cardmanagementsystem.repository.TransferRepository;
import com.farazpardazan.cardmanagementsystem.service.card.CardService;
import com.farazpardazan.cardmanagementsystem.service.notification.NotificationService;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentProviderException;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentProviderStrategy;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentStrategyFactory;
import com.farazpardazan.cardmanagementsystem.service.user.UserService;
import com.farazpardazan.cardmanagementsystem.web.dto.transfer.ReportDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author Hossein Baghshahi
 */
@ExtendWith(SpringExtension.class)
class DefaultTransferServiceTest {

    @Autowired
    private TransferService transferService;

    @MockBean
    private TransferRepository transferRepositoryMocked;

    @MockBean
    private PaymentStrategyFactory paymentStrategyFactoryMocked;

    @MockBean
    private NotificationService notificationServiceMocked;

    @MockBean
    private CardService cardServiceMocked;

    @MockBean
    private UserService userServiceMocked;

    @MockBean
    private PaymentProviderStrategy paymentProviderStrategyMocked;

    private Transfer dummyTransfer;

    private User dummyUser;

    private List<Transfer> dummyTransfers;

    private List<Card> dummyCards;

    @BeforeEach
    public void prepareDummies() {

        dummyUser = new User();
        dummyUser.setUsername("javier");
        dummyUser.setName("javier");
        dummyUser.setFamily("bardem");
        dummyUser.setMobileNumber("09124584934");

        dummyTransfer = new Transfer();
        dummyTransfer.setSourceCard("6219861034561234");
        dummyTransfer.setDestinationCard("6219861034568754");
        dummyTransfer.setStatus(Transfer.Status.PENDING);
        dummyTransfer.setAmount(1000000L);
        dummyTransfer.setCreatedDate(LocalDateTime.now());

        dummyTransfers = List.of(new Transfer("6219861034561234", "6219861034568754",
                        1000L, Transfer.Status.SUCCESSFUL, LocalDateTime.now().minusDays(10)),
                new Transfer("6219861034561234", "6219861034568754",
                        1000L, Transfer.Status.SUCCESSFUL, LocalDateTime.now().minusDays(9)),
                new Transfer("6219861034561234", "6219861034568754",
                        1000L, Transfer.Status.FAILED, LocalDateTime.now().minusDays(9)));

        dummyCards = List.of(new Card("mellat", "6219861034561234", dummyUser, true));
    }

    @Test
    void cardToCardMoneyTransfer_Successful_ShouldSendNotificationAndSetSuccessStatus() throws
            PaymentProviderException {
        String message = "successful";
        doReturn(paymentProviderStrategyMocked).when(paymentStrategyFactoryMocked)
                .getAppropriatePaymentProvider(dummyTransfer.getSourceCard());

        doReturn(Transfer.Status.SUCCESSFUL).when(paymentProviderStrategyMocked).moneyTransfer(dummyTransfer);
        dummyTransfer.setStatus(Transfer.Status.SUCCESSFUL);
        doReturn(dummyUser).when(userServiceMocked).getCurrentUser();
        doNothing().when(notificationServiceMocked).sendNotification(message, dummyUser);

        transferService.cardToCardMoneyTransfer(dummyTransfer);

        verify(transferRepositoryMocked, times(1)).save(dummyTransfer);
    }

    @Test
    void cardToCardMoneyTransfer_Failed_ShouldNotSendNotificationAndSetFailedStatus() throws PaymentProviderException {
        doReturn(paymentProviderStrategyMocked).when(paymentStrategyFactoryMocked)
                .getAppropriatePaymentProvider(dummyTransfer.getSourceCard());
        doThrow(new PaymentProviderException()).when(paymentProviderStrategyMocked).moneyTransfer(dummyTransfer);
        dummyTransfer.setStatus(Transfer.Status.FAILED);

        transferService.cardToCardMoneyTransfer(dummyTransfer);

        verify(transferRepositoryMocked, times(1)).save(dummyTransfer);
        verify(notificationServiceMocked, times(0)).sendNotification(any(), any());
    }

    @Test
    void moneyTransferReport_WithValidInput_ShouldReturnPageOfReportDtos() {
        List<ReportDto> reportDtos =
                List.of(new ReportDto(dummyCards.get(0).getCardNumber(),
                        2L, 1L));

        PageRequest pageRequest = PageRequest.of(0, 10);
        LocalDateTime from = LocalDateTime.now().minusDays(10);
        LocalDateTime to = LocalDateTime.now();

        doReturn(new PageImpl<>(dummyCards)).when(cardServiceMocked)
                .getAllActiveCards(pageRequest);

        doReturn(dummyTransfers).when(transferRepositoryMocked)
                .findAllBySourceCardAndCreatedDateBetween(dummyCards.get(0).getCardNumber(),
                        from, to);

        List<ReportDto> result = transferService.moneyTransferReport(pageRequest, from, to).toList();

        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(reportDtos);
    }

    @TestConfiguration
    public static class TransferServiceTestConfiguration {

        @Bean
        public TransferService transferService(PaymentStrategyFactory paymentStrategyFactory,
                                               TransferRepository transferRepository,
                                               CardService cardService, UserService userService,
                                               NotificationService notificationService) {
            return new DefaultTransferService(paymentStrategyFactory, transferRepository,
                    cardService, userService, notificationService);
        }

    }
}