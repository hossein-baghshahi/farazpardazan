package com.farazpardazan.cardmanagementsystem.web.dto.transfer;

/**
 * @author Hossein Baghshahi
 */
public class ReportDto {
    private String cardNumber;
    private Long successfulTransfersCount;
    private Long failedTransfersCount;

    public ReportDto(String cardNumber, Long successfulTransfersCount, Long failedTransfersCount) {
        this.cardNumber = cardNumber;
        this.successfulTransfersCount = successfulTransfersCount;
        this.failedTransfersCount = failedTransfersCount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getSuccessfulTransfersCount() {
        return successfulTransfersCount;
    }

    public void setSuccessfulTransfersCount(Long successfulTransfersCount) {
        this.successfulTransfersCount = successfulTransfersCount;
    }

    public Long getFailedTransfersCount() {
        return failedTransfersCount;
    }

    public void setFailedTransfersCount(Long failedTransfersCount) {
        this.failedTransfersCount = failedTransfersCount;
    }
}
