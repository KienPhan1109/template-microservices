package com.ptit.medicare_patient_service.service;

import com.ptit.medicare_patient_service.dto.request.PatientCreateRequest;
import com.ptit.medicare_patient_service.dto.response.PatientResponse;

import java.util.List;

public interface PatientService {
    List<PatientResponse> getAllPatients();
    PatientResponse getPatientById(Long id);
    PatientResponse createPatient(PatientCreateRequest request);
    void deletePatient(Long id);
    PatientResponse restorePatient(Long id);
    List<PatientResponse> getDeletedPatients();
}
