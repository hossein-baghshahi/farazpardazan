package com.farazpardazan.cardmanagementsystem.service.paymentprovider.mellat;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Hossein Baghshahi
 */
public class MellatPaymentDto {
    @JsonProperty("sourcePan")
    private String source;

    @JsonProperty("targetPan")
    private String destination;

    private String cvv2;

    @JsonProperty("expire")
    private String expirationDate;

    @JsonProperty("pin2")
    private String pin;

    private String amount;

    public MellatPaymentDto(String source, String destination, String cvv2,
                            String expirationDate, String pin, String amount) {
        this.source = source;
        this.destination = destination;
        this.cvv2 = cvv2;
        this.expirationDate = expirationDate;
        this.pin = pin;
        this.amount = amount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
