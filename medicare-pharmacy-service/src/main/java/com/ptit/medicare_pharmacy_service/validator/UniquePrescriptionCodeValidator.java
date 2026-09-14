package com.ptit.medicare_pharmacy_service.validator;

import com.ptit.medicare_pharmacy_service.repository.PrescriptionRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePrescriptionCodeValidator implements ConstraintValidator<UniquePrescriptionCode, String> {

    private final PrescriptionRepository prescriptionRepository;

    @Override
    public boolean isValid(String prescriptionCode, ConstraintValidatorContext context) {
        if (prescriptionCode == null || prescriptionCode.trim().isEmpty()) {
            return true;
        }
        return !prescriptionRepository.existsByPrescriptionCode(prescriptionCode.trim());
    }
}
