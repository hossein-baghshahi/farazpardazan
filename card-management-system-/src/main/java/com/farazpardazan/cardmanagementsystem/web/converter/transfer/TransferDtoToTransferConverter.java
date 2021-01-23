package com.farazpardazan.cardmanagementsystem.web.converter.transfer;

import com.farazpardazan.cardmanagementsystem.domain.Transfer;
import com.farazpardazan.cardmanagementsystem.web.dto.transfer.TransferDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Hossein Baghshahi
 */
@Component
public class TransferDtoToTransferConverter implements Converter<TransferDto, Transfer> {
    @Override
    public Transfer convert(TransferDto transferDto) {
        Transfer transfer = new Transfer();
        transfer.setSourceCard(transferDto.getSourceCardNumber());
        transfer.setDestinationCard(transferDto.getDestinationCardNumber());
        transfer.setAmount(transferDto.getAmount());
        transfer.setStatus(Transfer.Status.PENDING);
        transfer.setCvv(transferDto.getCvv());
        transfer.setPin(transferDto.getPin());
        transfer.setExpirationDate(transferDto.getExpirationDate());
        return transfer;
    }
}
