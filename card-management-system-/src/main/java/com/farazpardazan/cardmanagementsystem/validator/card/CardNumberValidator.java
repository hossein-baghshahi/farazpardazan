package com.farazpardazan.cardmanagementsystem.validator.card;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Hossein Baghshahi
 */
public class CardNumberValidator implements ConstraintValidator<CardNumber, String> {
    @Override
    public void initialize(CardNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.length() == 16 && value.matches("[0-9]+"))
            return true;
        return false;
    }
}
