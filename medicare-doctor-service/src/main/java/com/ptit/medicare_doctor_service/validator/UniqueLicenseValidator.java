package com.ptit.medicare_doctor_service.validator;

import com.ptit.medicare_doctor_service.repository.DoctorRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueLicenseValidator implements ConstraintValidator<UniqueLicense, String> {
    private final DoctorRepository doctorRepository;

    @Override
    public boolean isValid(String licenseNumber, ConstraintValidatorContext context) {
        if (licenseNumber == null || licenseNumber.trim().isEmpty()) {
            return true;
        }
        return !doctorRepository.existsByLicenseNumber(licenseNumber.trim());
    }
}
