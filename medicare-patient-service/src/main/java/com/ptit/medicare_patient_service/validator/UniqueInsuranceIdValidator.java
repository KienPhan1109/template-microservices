package com.ptit.medicare_patient_service.validator;

import com.ptit.medicare_patient_service.repository.PatientRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueInsuranceIdValidator implements ConstraintValidator<UniqueInsuranceId, String> {
    private final PatientRepository patientRepository;

    @Override
    public boolean isValid(String insuranceId, ConstraintValidatorContext context) {
        if (insuranceId == null || insuranceId.trim().isEmpty()) {
            return true;
        }
        return !patientRepository.existsByInsuranceId(insuranceId.trim());
    }
}
