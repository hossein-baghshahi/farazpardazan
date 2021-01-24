package com.farazpardazan.cardmanagementsystem.validator.card;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Hossein Baghshahi
 */
@Constraint(validatedBy = {CardNumberValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CardNumber {

    String message() default "{card.number.format.incorrect}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
