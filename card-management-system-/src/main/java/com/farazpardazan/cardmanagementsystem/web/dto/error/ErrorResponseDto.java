package com.farazpardazan.cardmanagementsystem.web.dto.error;

import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * @author Hossein Baghshahi
 */
public class ErrorResponseDto {

    /**
     * HTTP status code.
     */
    private final HttpStatus httpStatus;

    /**
     * List of errors
     */
    private final List<String> errors;

    /**
     * Constructor
     */
    public ErrorResponseDto(HttpStatus httpStatus, List<String> errors) {
        this.httpStatus = httpStatus;
        this.errors = errors;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public List<String> getErrors() {
        return errors;
    }
}
