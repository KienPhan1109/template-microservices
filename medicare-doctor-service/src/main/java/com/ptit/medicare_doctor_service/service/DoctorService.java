package com.ptit.medicare_doctor_service.service;

import com.ptit.medicare_doctor_service.dto.request.DoctorCreateRequest;
import com.ptit.medicare_doctor_service.dto.request.DoctorFilterRequest;
import com.ptit.medicare_doctor_service.dto.response.DoctorResponse;

import java.util.List;

public interface DoctorService {
    List<DoctorResponse> getDoctors(DoctorFilterRequest filter);
    List<DoctorResponse> getAllDoctors();
    DoctorResponse getDoctorById(Long id);
    DoctorResponse createDoctor(DoctorCreateRequest request);
    void deleteDoctor(Long id);
    DoctorResponse restoreDoctor(Long id);
    List<DoctorResponse> getDeletedDoctors();
    List<DoctorResponse> getDoctorsByDepartmentId(Long departmentId);
}
