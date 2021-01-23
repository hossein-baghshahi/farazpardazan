package com.farazpardazan.cardmanagementsystem.web.converter.transfer;

import com.farazpardazan.cardmanagementsystem.domain.Transfer;
import com.farazpardazan.cardmanagementsystem.web.dto.transfer.TransferResultDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Hossein Baghshahi
 */
@Component
public class TransferToTransferResultDtoConverter implements Converter<Transfer, TransferResultDto> {
    @Override
    public TransferResultDto convert(Transfer transfer) {
        TransferResultDto transferResultDto = new TransferResultDto();
        transferResultDto.setStatus(transfer.getStatus());
        return transferResultDto;
    }
}
