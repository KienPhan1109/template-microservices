package com.ptit.medicare_doctor_service.repository;

import com.ptit.medicare_doctor_service.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {
    boolean existsByLicenseNumber(String licenseNumber);
    boolean existsByLicenseNumberAndIdNot(String licenseNumber, Long id);

    boolean existsByPhone(String phone);
    boolean existsByPhoneAndIdNot(String phone, Long id);

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<Doctor> findByIdAndIsDeletedFalse(Long id);
    List<Doctor> findAllByIsDeletedFalse();
    List<Doctor> findAllByIsDeletedTrue();

    List<Doctor> findAllByDepartmentIdAndIsDeletedFalse(Long departmentId);
}
