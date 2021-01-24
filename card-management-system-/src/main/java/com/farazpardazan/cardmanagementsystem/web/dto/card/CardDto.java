package com.farazpardazan.cardmanagementsystem.web.dto.card;

import com.farazpardazan.cardmanagementsystem.validator.card.CardNumber;

import javax.validation.constraints.NotBlank;

/**
 * @author Hossein Baghshahi
 */
public class CardDto {

    @NotBlank(message = "{card.name.empty")
    private String name;

    @NotBlank(message = "{card.number.empty}")
    @CardNumber
    private String cardNumber;

    public CardDto() {
    }

    public CardDto(@NotBlank(message = "{card.name.empty") String name,
                   @NotBlank(message = "{card.number.empty}") String cardNumber) {
        this.name = name;
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
