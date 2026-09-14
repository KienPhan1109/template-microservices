package com.ptit.medicare_pharmacy_service.validator;

import com.ptit.medicare_pharmacy_service.enums.MedicineStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class MedicineStatusValidator implements ConstraintValidator<ValidMedicineStatus, String> {
    private String customMessage;

    @Override
    public void initialize(ValidMedicineStatus constraintAnnotation) {
        this.customMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        boolean valid = Arrays.stream(MedicineStatus.values())
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
