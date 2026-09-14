package com.ptit.medicare_doctor_service.repository;

import com.ptit.medicare_doctor_service.entity.Department;
import com.ptit.medicare_doctor_service.enums.DepartmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {
    boolean existsByDepartmentCode(String departmentCode);
    boolean existsByDepartmentCodeAndIdNot(String departmentCode, Long id);

    boolean existsByDepartmentName(String departmentName);
    boolean existsByDepartmentNameAndIdNot(String departmentName, Long id);

    boolean existsByLocation(String location);
    boolean existsByLocationAndIdNot(String location, Long id);

    Optional<Department> findByIdAndIsDeletedFalse(Long id);
    Optional<Department> findByDepartmentCodeAndIsDeletedFalse(String departmentCode);

    List<Department> findAllByIsDeletedFalse();
    List<Department> findAllByStatusAndIsDeletedFalse(DepartmentStatus status);
    List<Department> findAllByIsDeletedTrue();
}
