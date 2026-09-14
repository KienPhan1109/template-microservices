package com.ptit.medicare_doctor_service.mapper;

import com.ptit.medicare_doctor_service.dto.request.DoctorCreateRequest;
import com.ptit.medicare_doctor_service.dto.response.DoctorResponse;
import com.ptit.medicare_doctor_service.entity.Department;
import com.ptit.medicare_doctor_service.entity.Doctor;
import com.ptit.medicare_doctor_service.enums.DoctorGender;
import com.ptit.medicare_doctor_service.enums.DoctorStatus;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {
    public DoctorResponse toResponse(Doctor entity) {
        if (entity == null) {
            return null;
        }
        Department dept = entity.getDepartment();
        return DoctorResponse.builder()
                .id(entity.getId())
                .licenseNumber(entity.getLicenseNumber())
                .fullName(entity.getFullName())
                .dateOfBirth(entity.getDateOfBirth())
                .gender(entity.getGender())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .degree(entity.getDegree())
                .departmentId(dept != null ? dept.getId() : null)
                .departmentCode(dept != null ? dept.getDepartmentCode() : null)
                .departmentName(dept != null ? dept.getDepartmentName() : null)
                .departmentLocation(dept != null ? dept.getLocation() : null)
                .experienceYears(entity.getExperienceYears())
                .bio(entity.getBio())
                .consultationFee(entity.getConsultationFee())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public Doctor toEntity(DoctorCreateRequest request, Department department) {
        if (request == null) {
            return null;
        }
        DoctorStatus status = DoctorStatus.ACTIVE;
        if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            try {
                status = DoctorStatus.valueOf(request.getStatus().trim().toUpperCase());
            } catch (IllegalArgumentException ignored) {
                status = DoctorStatus.ACTIVE;
            }
        }

        DoctorGender gender = null;
        if (request.getGender() != null && !request.getGender().trim().isEmpty()) {
            try {
                gender = DoctorGender.valueOf(request.getGender().trim().toUpperCase());
            } catch (IllegalArgumentException ignored) {}
        }

        return Doctor.builder()
                .licenseNumber(request.getLicenseNumber().trim())
                .fullName(request.getFullName().trim())
                .dateOfBirth(request.getDateOfBirth())
                .gender(gender)
                .phone(request.getPhone().trim())
                .email(request.getEmail().trim())
                .address(request.getAddress())
                .degree(request.getDegree().trim())
                .department(department)
                .experienceYears(request.getExperienceYears() != null ? request.getExperienceYears() : 0)
                .bio(request.getBio())
                .consultationFee(request.getConsultationFee())
                .status(status)
                .isDeleted(false)
                .build();
    }
}
