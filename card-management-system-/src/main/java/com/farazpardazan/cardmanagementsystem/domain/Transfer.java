package com.farazpardazan.cardmanagementsystem.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author Hossein Baghshahi
 */

@Entity
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "{transfer.source.card.number.empty}")
    @Column(name = "source_card_number",nullable = false)
    private String sourceCard;

    @NotNull(message = "{transfer.destination.card.number.empty}")
    @Column(name = "destination_card_number",nullable = false)
    private String destinationCard;

    @NotNull(message = "{transfer.amount.empty}")
    @Column(nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{transfer.status.null}")
    @Column(nullable = false)
    private Status status = Status.PENDING;

    @NotNull(message = "{transfer.created_date_time.null}")
    @Column(name = "created_date" ,nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Transient
    private String pin;

    @Transient
    private String cvv;

    @Transient
    private String expirationDate;

    public Transfer() {
    }

    public Transfer(String sourceCard, String destinationCard, Long amount,
                    Status status, LocalDateTime createdDate) {
        this.sourceCard = sourceCard;
        this.destinationCard = destinationCard;
        this.amount = amount;
        this.status = status;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public String getSourceCard() {
        return sourceCard;
    }

    public void setSourceCard(String sourceCard) {
        this.sourceCard = sourceCard;
    }

    public String getDestinationCard() {
        return destinationCard;
    }

    public void setDestinationCard(String destinationCard) {
        this.destinationCard = destinationCard;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime dateTime) {
        this.createdDate = dateTime;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public enum Status {

        SUCCESSFUL,

        FAILED,

        PENDING
    }
}
