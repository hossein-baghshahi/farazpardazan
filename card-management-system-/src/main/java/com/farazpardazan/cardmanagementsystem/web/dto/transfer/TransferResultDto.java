package com.farazpardazan.cardmanagementsystem.web.dto.transfer;

import com.farazpardazan.cardmanagementsystem.domain.Transfer;

/**
 * @author Hossein Baghshahi
 */
public class TransferResultDto {
    private Transfer.Status status;

    public Transfer.Status getStatus() {
        return status;
    }

    public void setStatus(Transfer.Status status) {
        this.status = status;
    }
}
