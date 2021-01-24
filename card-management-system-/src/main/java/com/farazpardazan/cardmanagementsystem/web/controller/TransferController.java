package com.farazpardazan.cardmanagementsystem.web.controller;

import com.farazpardazan.cardmanagementsystem.domain.Transfer;
import com.farazpardazan.cardmanagementsystem.service.transfer.TransferService;
import com.farazpardazan.cardmanagementsystem.web.dto.transfer.ReportDto;
import com.farazpardazan.cardmanagementsystem.web.dto.transfer.TransferDto;
import com.farazpardazan.cardmanagementsystem.web.dto.transfer.TransferResultDto;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author Hossein Baghshahi
 */
@RestController
@RequestMapping("/api/transfer")
public class TransferController {
    private final TransferService transferService;

    private final ConversionService conversionService;

    public TransferController(TransferService transferService, ConversionService conversionService) {
        this.transferService = transferService;
        this.conversionService = conversionService;
    }

    @PostMapping("/card-to-card")
    public ResponseEntity<TransferResultDto> cardToCardTransfer(@Validated @RequestBody TransferDto transferDto) {
        Transfer transfer =
                transferService.cardToCardMoneyTransfer(conversionService.convert(transferDto, Transfer.class));

        return ResponseEntity.ok(conversionService.convert(transfer, TransferResultDto.class));
    }

    @GetMapping("/report")
    public ResponseEntity<Page<ReportDto>> getTransferReport(
            @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime to,
            Pageable pageable) {
        Page<ReportDto> reports = transferService.moneyTransferReport(pageable, from, to);

        return ResponseEntity.ok(reports);
    }
}
