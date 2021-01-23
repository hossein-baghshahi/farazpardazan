package com.farazpardazan.cardmanagementsystem.web.dto.transfer;

import com.farazpardazan.cardmanagementsystem.validator.card.CardNumber;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Hossein Baghshahi
 */
public class TransferDto {

    @NotBlank(message = "{transfer.source.card.number.empty}")
    @CardNumber
    private String sourceCardNumber;

    @NotBlank(message = "{transfer.destination.card.number.empty}")
    @CardNumber
    private String destinationCardNumber;

    @NotBlank(message = "{transfer.pin.card.empty}")
    private String pin;

    @NotBlank(message = "{transfer.pin.expiration_date.empty}")
    private String expirationDate;

    @NotNull(message = "{transfer.amount.null}")
    private Long amount;

    @NotBlank(message = "transfer.cvv.empty")
    private String cvv;

    public String getSourceCardNumber() {
        return sourceCardNumber;
    }

    public void setSourceCardNumber(String sourceCardNumber) {
        this.sourceCardNumber = sourceCardNumber;
    }

    public String getDestinationCardNumber() {
        return destinationCardNumber;
    }

    public void setDestinationCardNumber(String destinationCardNumber) {
        this.destinationCardNumber = destinationCardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
