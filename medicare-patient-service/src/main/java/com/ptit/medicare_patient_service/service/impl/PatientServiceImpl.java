package com.ptit.medicare_patient_service.service.impl;

import com.ptit.medicare_patient_service.dto.request.PatientCreateRequest;
import com.ptit.medicare_patient_service.dto.response.PatientResponse;
import com.ptit.medicare_patient_service.entity.Patient;
import com.ptit.medicare_patient_service.exception.PatientNotFoundException;
import com.ptit.medicare_patient_service.mapper.PatientMapper;
import com.ptit.medicare_patient_service.repository.PatientRepository;
import com.ptit.medicare_patient_service.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> getAllPatients() {
        return patientRepository.findAllByIsDeletedFalse()
                .stream()
                .map(patientMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        return patientMapper.toResponse(patient);
    }

    @Override
    public PatientResponse createPatient(PatientCreateRequest request) {
        Patient patient = patientMapper.toEntity(request);
        patient.setDeleted(false);
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toResponse(savedPatient);
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        patient.setDeleted(true);
        patientRepository.save(patient);
    }

    @Override
    public PatientResponse restorePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        patient.setDeleted(false);
        Patient restoredPatient = patientRepository.save(patient);
        return patientMapper.toResponse(restoredPatient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> getDeletedPatients() {
        return patientRepository.findAllByIsDeletedTrue()
                .stream()
                .map(patientMapper::toResponse)
                .toList();
    }
}
