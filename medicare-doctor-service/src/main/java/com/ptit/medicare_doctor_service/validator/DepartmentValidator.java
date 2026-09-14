package com.ptit.medicare_doctor_service.validator;

import com.ptit.medicare_doctor_service.enums.DoctorDepartment;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DepartmentValidator implements ConstraintValidator<ValidDepartment, String> {
    private String customMessage;

    @Override
    public void initialize(ValidDepartment constraintAnnotation) {
        this.customMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        DoctorDepartment department = DoctorDepartment.fromString(value);
        if (department == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(customMessage)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
