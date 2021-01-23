package com.farazpardazan.cardmanagementsystem.service.transfer;

import com.farazpardazan.cardmanagementsystem.domain.Card;
import com.farazpardazan.cardmanagementsystem.domain.Transfer;
import com.farazpardazan.cardmanagementsystem.repository.TransferRepository;
import com.farazpardazan.cardmanagementsystem.service.card.CardService;
import com.farazpardazan.cardmanagementsystem.service.notification.NotificationService;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentProviderException;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentProviderStrategy;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentStrategyFactory;
import com.farazpardazan.cardmanagementsystem.service.paymentprovider.PaymentStrategyName;
import com.farazpardazan.cardmanagementsystem.service.user.UserService;
import com.farazpardazan.cardmanagementsystem.web.dto.transfer.ReportDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hossein Baghshahi
 */
@Service
public class DefaultTransferService implements TransferService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultTransferService.class);

    private final PaymentStrategyFactory paymentStrategyFactory;

    private final TransferRepository transferRepository;

    private final CardService cardService;

    private final UserService userService;

    private final NotificationService notificationService;

    public DefaultTransferService(PaymentStrategyFactory paymentStrategyFactory,
                                  TransferRepository transferRepository, CardService cardService,
                                  UserService userService, NotificationService notificationService) {
        this.paymentStrategyFactory = paymentStrategyFactory;
        this.transferRepository = transferRepository;
        this.cardService = cardService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @Transactional
    @Override
    public Transfer cardToCardMoneyTransfer(Transfer transfer) {
        PaymentProviderStrategy paymentProvider = paymentStrategyFactory
                .getAppropriatePaymentProvider(transfer.getSourceCard());

        try {
            paymentProvider.moneyTransfer(transfer);
            transfer.setStatus(Transfer.Status.SUCCESSFUL);
            notificationService.sendNotification("successful payment from" + transfer.getSourceCard() +
                            " to " + transfer.getDestinationCard() + "amount: " + transfer.getAmount(),
                    userService.getCurrentUser());
        } catch (PaymentProviderException e) {
            transfer.setStatus(Transfer.Status.FAILED);
            logger.error("Money transfer failed",e);
        }

        transfer = transferRepository.save(transfer);

        return transfer;
    }

    @Override
    public Page<ReportDto> moneyTransferReport(Pageable pageable, LocalDateTime fromDate, LocalDateTime toDate) {
        Page<Card> cards = cardService.getAllActiveCards(pageable);
        List<ReportDto> reportDtos = new ArrayList<>();

        cards.forEach(card -> {
            List<Transfer> transfers = transferRepository
                    .findAllBySourceCardAndCreatedDateBetween(card.getCardNumber(), fromDate, toDate);
            long successfulTransfers = transfers.stream()
                    .filter(transfer -> transfer.getStatus().equals(Transfer.Status.SUCCESSFUL)).count();
            long failedTransfers = transfers.stream()
                    .filter(transfer -> transfer.getStatus().equals(Transfer.Status.FAILED)).count();
            reportDtos.add(new ReportDto(card.getCardNumber(), successfulTransfers, failedTransfers));

        });

        return new PageImpl<>(reportDtos);
    }
}
