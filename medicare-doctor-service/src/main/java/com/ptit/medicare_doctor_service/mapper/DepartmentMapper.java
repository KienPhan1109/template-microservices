package com.ptit.medicare_doctor_service.mapper;

import com.ptit.medicare_doctor_service.dto.request.DepartmentCreateRequest;
import com.ptit.medicare_doctor_service.dto.request.DepartmentUpdateRequest;
import com.ptit.medicare_doctor_service.dto.response.DepartmentResponse;
import com.ptit.medicare_doctor_service.entity.Department;
import com.ptit.medicare_doctor_service.enums.DepartmentStatus;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public DepartmentResponse toResponse(Department entity) {
        if (entity == null) {
            return null;
        }
        return DepartmentResponse.builder()
                .id(entity.getId())
                .departmentCode(entity.getDepartmentCode())
                .departmentName(entity.getDepartmentName())
                .description(entity.getDescription())
                .location(entity.getLocation())
                .phone(entity.getPhone())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public Department toEntity(DepartmentCreateRequest request) {
        if (request == null) {
            return null;
        }
        DepartmentStatus status = DepartmentStatus.ACTIVE;
        if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            try {
                status = DepartmentStatus.valueOf(request.getStatus().trim().toUpperCase());
            } catch (IllegalArgumentException ignored) {
                status = DepartmentStatus.ACTIVE;
            }
        }

        return Department.builder()
                .departmentCode(request.getDepartmentCode().trim().toUpperCase())
                .departmentName(request.getDepartmentName().trim())
                .description(request.getDescription())
                .location(request.getLocation())
                .phone(request.getPhone())
                .status(status)
                .isDeleted(false)
                .build();
    }

    public void updateEntityFromRequest(Department entity, DepartmentUpdateRequest request) {
        if (request == null || entity == null) {
            return;
        }
        if (request.getDepartmentName() != null && !request.getDepartmentName().trim().isEmpty()) {
            entity.setDepartmentName(request.getDepartmentName().trim());
        }
        if (request.getDescription() != null) {
            entity.setDescription(request.getDescription());
        }
        if (request.getLocation() != null) {
            entity.setLocation(request.getLocation());
        }
        if (request.getPhone() != null) {
            entity.setPhone(request.getPhone());
        }
        if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            try {
                entity.setStatus(DepartmentStatus.valueOf(request.getStatus().trim().toUpperCase()));
            } catch (IllegalArgumentException ignored) {}
        }
    }
}
