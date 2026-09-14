package com.ptit.medicare_patient_service.repository;

import com.ptit.medicare_patient_service.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByPhone(String phone);
    boolean existsByPhoneAndIdNot(String phone, Long id);

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByInsuranceId(String insuranceId);
    boolean existsByInsuranceIdAndIdNot(String insuranceId, Long id);

    Optional<Patient> findByIdAndIsDeletedFalse(Long id);
    List<Patient> findAllByIsDeletedFalse();
    List<Patient> findAllByIsDeletedTrue();
}
