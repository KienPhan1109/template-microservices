package com.ptit.medicare_pharmacy_service.service;

import com.ptit.medicare_pharmacy_service.dto.request.PrescriptionCreateRequest;
import com.ptit.medicare_pharmacy_service.dto.request.PrescriptionUpdateRequest;
import com.ptit.medicare_pharmacy_service.dto.response.PrescriptionResponse;
import com.ptit.medicare_pharmacy_service.enums.PrescriptionStatus;

import java.util.List;

public interface PrescriptionService {

    List<PrescriptionResponse> getAllPrescriptions(PrescriptionStatus status, Long patientId, Long doctorId, Long medicalId);

    PrescriptionResponse getPrescriptionById(Long id);

    PrescriptionResponse getPrescriptionByCode(String code);

    List<PrescriptionResponse> getPrescriptionsByPatientId(Long patientId);

    List<PrescriptionResponse> getPrescriptionsByMedicalId(Long medicalId);

    List<PrescriptionResponse> getDeletedPrescriptions();

    PrescriptionResponse createPrescription(PrescriptionCreateRequest request);

    PrescriptionResponse updatePrescription(Long id, PrescriptionUpdateRequest request);

    PrescriptionResponse dispensePrescription(Long id);

    PrescriptionResponse cancelPrescription(Long id, String reason);

    void deletePrescription(Long id);

    PrescriptionResponse restorePrescription(Long id);
}
