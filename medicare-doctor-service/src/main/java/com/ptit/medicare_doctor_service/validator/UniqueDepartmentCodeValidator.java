package com.ptit.medicare_doctor_service.validator;

import com.ptit.medicare_doctor_service.repository.DepartmentRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueDepartmentCodeValidator implements ConstraintValidator<UniqueDepartmentCode, String> {
    private final DepartmentRepository departmentRepository;
    private String customMessage;

    @Override
    public void initialize(UniqueDepartmentCode constraintAnnotation) {
        this.customMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        if (code == null || code.trim().isEmpty()) {
            return true;
        }
        boolean exists = departmentRepository.existsByDepartmentCode(code.trim().toUpperCase());
        if (exists) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(customMessage)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
