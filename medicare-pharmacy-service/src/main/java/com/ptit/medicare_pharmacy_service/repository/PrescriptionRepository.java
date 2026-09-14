package com.ptit.medicare_pharmacy_service.repository;

import com.ptit.medicare_pharmacy_service.entity.Prescription;
import com.ptit.medicare_pharmacy_service.enums.PrescriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    boolean existsByPrescriptionCode(String prescriptionCode);

    boolean existsByMedicalId(Long medicalId);

    Optional<Prescription> findByIdAndIsDeletedFalse(Long id);

    Optional<Prescription> findByPrescriptionCodeAndIsDeletedFalse(String prescriptionCode);

    List<Prescription> findAllByIsDeletedFalseOrderByCreatedAtDesc();

    List<Prescription> findAllByIsDeletedTrueOrderByUpdatedAtDesc();

    List<Prescription> findAllByPatientIdAndIsDeletedFalseOrderByCreatedAtDesc(Long patientId);

    List<Prescription> findAllByDoctorIdAndIsDeletedFalseOrderByCreatedAtDesc(Long doctorId);

    List<Prescription> findAllByMedicalIdAndIsDeletedFalseOrderByCreatedAtDesc(Long medicalId);

    List<Prescription> findAllByStatusAndIsDeletedFalseOrderByCreatedAtDesc(PrescriptionStatus status);
}
