package com.ptit.medicare_doctor_service.validator;

import com.ptit.medicare_doctor_service.repository.DepartmentRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueDepartmentLocationValidator implements ConstraintValidator<UniqueDepartmentLocation, String> {
    private final DepartmentRepository departmentRepository;
    private String customMessage;

    @Override
    public void initialize(UniqueDepartmentLocation constraintAnnotation) {
        this.customMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String location, ConstraintValidatorContext context) {
        if (location == null || location.trim().isEmpty()) {
            return true;
        }
        boolean exists = departmentRepository.existsByLocation(location.trim());
        if (exists) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(customMessage)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
