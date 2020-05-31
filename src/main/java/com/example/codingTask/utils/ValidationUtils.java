package com.example.codingTask.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

/**
 * @author BUmerle
 */

public class ValidationUtils {
    private final static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public static <T> void validate(final T validationSubject) {
        final Set<ConstraintViolation<T>> violations = factory.getValidator().validate(validationSubject, Default.class);
        if (!violations.isEmpty()) {
            throwException(validationSubject, violations);
        }
    }

    private static <T> void throwException(T validationSubject, final Set<ConstraintViolation<T>> violations) {
        final StringBuilder stringBuilder = new StringBuilder(100);
        stringBuilder.append("Validation of class: ")
                .append(validationSubject.getClass());
        for (final ConstraintViolation<T> violation : violations) {
            stringBuilder.append("\n Property: ")
                    .append(violation.getPropertyPath())
                    .append(" ")
                    .append(violation.getMessage());
        }
        throw new ValidationException(stringBuilder.toString());
    }

}
