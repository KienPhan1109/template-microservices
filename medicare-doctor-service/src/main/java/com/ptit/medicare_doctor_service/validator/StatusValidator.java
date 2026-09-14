package com.ptit.medicare_doctor_service.validator;

import com.ptit.medicare_doctor_service.enums.DoctorStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class StatusValidator implements ConstraintValidator<ValidStatus, String> {
    private String customMessage;

    @Override
    public void initialize(ValidStatus constraintAnnotation) {
        this.customMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        boolean valid = Arrays.stream(DoctorStatus.values())
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
