package com.farazpardazan.cardmanagementsystem.web.error;

import com.farazpardazan.cardmanagementsystem.web.dto.error.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

/**
 * @author Hossein Baghshahi
 */
@RestControllerAdvice
public class ErrorHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    /**
     * Will be matched will all texts surrounding with curly braces
     *
     * @see #stripBraces(String)
     */
    private static final Pattern CODE_PATTERN = Pattern.compile("\\{(.*?)}");

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleError(Exception exception) {
        ErrorResponseDto errorResponse =
                new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, List.of(exception.getMessage()));

        return ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        List<String> errorCodes = getErrorCodes(allErrors);
        ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.BAD_REQUEST, errorCodes);
        return ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
    }

    private List<String> getErrorCodes(List<ObjectError> errors) {
        return errors.stream().map(ObjectError::getDefaultMessage).map(this::stripBraces).collect(toList());
    }

    /**
     * Remove possible surrounding {}s from the input text
     *
     * @param code The text to strip
     * @return {}-free text
     */
    private String stripBraces(String code) {
        if (code == null) return null;

        Matcher matcher = CODE_PATTERN.matcher(code);
        if (matcher.find()) {
            return matcher.replaceFirst("$1");
        }

        return code;
    }
}
