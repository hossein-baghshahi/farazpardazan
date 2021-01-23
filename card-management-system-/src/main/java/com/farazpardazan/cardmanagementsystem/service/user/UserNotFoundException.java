package com.farazpardazan.cardmanagementsystem.service.user;

/**
 * @author Hossein Baghshahi
 */
public class UserNotFoundException extends RuntimeException{

    private static final String defaultErrorMessage = "User not found.";

    public UserNotFoundException() {
        super(defaultErrorMessage);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
