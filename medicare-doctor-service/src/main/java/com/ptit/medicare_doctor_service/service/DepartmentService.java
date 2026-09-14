package com.ptit.medicare_doctor_service.service;

import com.ptit.medicare_doctor_service.dto.request.DepartmentCreateRequest;
import com.ptit.medicare_doctor_service.dto.request.DepartmentUpdateRequest;
import com.ptit.medicare_doctor_service.dto.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    List<DepartmentResponse> getAllDepartments();
    List<DepartmentResponse> getActiveDepartments();
    DepartmentResponse getDepartmentById(Long id);
    DepartmentResponse getDepartmentByCode(String code);
    DepartmentResponse createDepartment(DepartmentCreateRequest request);
    DepartmentResponse updateDepartment(Long id, DepartmentUpdateRequest request);
    void deleteDepartment(Long id);
    DepartmentResponse restoreDepartment(Long id);
    List<DepartmentResponse> getDeletedDepartments();
}
