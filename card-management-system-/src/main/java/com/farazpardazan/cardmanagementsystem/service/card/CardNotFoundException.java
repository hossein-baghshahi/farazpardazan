package com.farazpardazan.cardmanagementsystem.service.card;

/**
 * @author Hossein Baghshahi
 */
public class CardNotFoundException extends RuntimeException {

    private static final String defaultErrorMessage = "Card not found.";

    public CardNotFoundException() {
        super(defaultErrorMessage);
    }

    public CardNotFoundException(String message) {
        super(message);
    }
}
