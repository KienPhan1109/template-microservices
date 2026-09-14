package com.ptit.medicare_appointment_service.validator;

import com.ptit.medicare_appointment_service.enums.AppointmentStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidAppointmentStatusValidator implements ConstraintValidator<ValidAppointmentStatus, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // Cho phép rỗng để áp dụng giá trị mặc định SCHEDULED
        }
        return AppointmentStatus.isValid(value);
    }
}
