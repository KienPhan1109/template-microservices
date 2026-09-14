package com.ptit.medicare_patient_service.mapper;

import com.ptit.medicare_patient_service.dto.request.PatientCreateRequest;
import com.ptit.medicare_patient_service.dto.response.PatientResponse;
import com.ptit.medicare_patient_service.entity.Patient;
import com.ptit.medicare_patient_service.enums.PatientBlood;
import com.ptit.medicare_patient_service.enums.PatientGender;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {
    public PatientResponse toResponse(Patient entity) {
        if (entity == null) {
            return null;
        }
        return PatientResponse.builder()
            .id(entity.getId())
            .fullName(entity.getFullName())
            .dateOfBirth(entity.getDateOfBirth())
            .gender(entity.getGender())
            .phone(entity.getPhone())
            .email(entity.getEmail())
            .address(entity.getAddress())
            .insuranceId(entity.getInsuranceId())
            .bloodType(entity.getBloodType())
            .emergencyContactName(entity.getEmergencyContactName())
            .emergencyContactPhone(entity.getEmergencyContactPhone())
            .build();
    }

    public Patient toEntity(PatientCreateRequest request) {
        if (request == null) {
            return null;
        }
        return Patient.builder()
            .fullName(request.getFullName())
            .dateOfBirth(request.getDateOfBirth())
            .gender(request.getGender() != null ? PatientGender.valueOf(request.getGender().trim().toUpperCase()) : null)
            .phone(request.getPhone())
            .email(request.getEmail())
            .address(request.getAddress())
            .insuranceId(request.getInsuranceId())
            .bloodType(request.getBloodType() != null ? PatientBlood.valueOf(request.getBloodType().trim().toUpperCase()) : null)
            .emergencyContactName(request.getEmergencyContactName())
            .emergencyContactPhone(request.getEmergencyContactPhone())
            .build();
    }
}
