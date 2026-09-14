package com.ptit.medicare_pharmacy_service.validator;

import com.ptit.medicare_pharmacy_service.repository.MedicineRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueMedicineCodeValidator implements ConstraintValidator<UniqueMedicineCode, String> {

    private final MedicineRepository medicineRepository;

    @Override
    public boolean isValid(String medicineCode, ConstraintValidatorContext context) {
        if (medicineCode == null || medicineCode.trim().isEmpty()) {
            return true;
        }
        return !medicineRepository.existsByMedicineCode(medicineCode.trim());
    }
}
