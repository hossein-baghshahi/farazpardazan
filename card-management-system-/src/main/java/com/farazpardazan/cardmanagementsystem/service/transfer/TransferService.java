package com.farazpardazan.cardmanagementsystem.service.transfer;

import com.farazpardazan.cardmanagementsystem.domain.Transfer;
import com.farazpardazan.cardmanagementsystem.web.dto.transfer.ReportDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Hossein Baghshahi
 */
public interface TransferService {

    public Transfer cardToCardMoneyTransfer(Transfer transfer);

    public Page<ReportDto> moneyTransferReport(Pageable pageable, LocalDateTime fromDate, LocalDateTime toDate);
}
