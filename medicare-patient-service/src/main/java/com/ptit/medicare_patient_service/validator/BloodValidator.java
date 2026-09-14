package com.ptit.medicare_patient_service.validator;

import com.ptit.medicare_patient_service.enums.PatientBlood;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class BloodValidator implements ConstraintValidator<ValidBlood, String> {
    private String customMessage;

    @Override
    public void initialize(ValidBlood constraintAnnotation) {
        this.customMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        boolean valid = Arrays.stream(PatientBlood.values())
                .anyMatch(blood -> blood.name().equalsIgnoreCase(value.trim()));

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(customMessage)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
