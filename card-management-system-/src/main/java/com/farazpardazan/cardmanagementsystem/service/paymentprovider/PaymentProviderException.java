package com.farazpardazan.cardmanagementsystem.service.paymentprovider;

/**
 * @author Hossein Baghshahi
 */
public class PaymentProviderException extends Exception {

    public PaymentProviderException() {
    }

    /**
     * Constructor
     */
    public PaymentProviderException(String message) {
        super(message);
    }
}
