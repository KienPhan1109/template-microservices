package com.ptit.medicare_pharmacy_service.validator;

import com.ptit.medicare_pharmacy_service.enums.PrescriptionStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class PrescriptionStatusValidator implements ConstraintValidator<ValidPrescriptionStatus, String> {
    private String customMessage;

    @Override
    public void initialize(ValidPrescriptionStatus constraintAnnotation) {
        this.customMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        boolean valid = Arrays.stream(PrescriptionStatus.values())
                .anyMatch(status -> status.name().equalsIgnoreCase(value.trim()));

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(customMessage)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
