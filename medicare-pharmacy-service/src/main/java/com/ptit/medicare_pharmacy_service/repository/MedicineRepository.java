package com.ptit.medicare_pharmacy_service.repository;

import com.ptit.medicare_pharmacy_service.entity.Medicine;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    boolean existsByMedicineCode(String medicineCode);

    boolean existsByMedicineCodeAndIdNot(String medicineCode, Long id);

    Optional<Medicine> findByIdAndIsDeletedFalse(Long id);

    Optional<Medicine> findByMedicineCodeAndIsDeletedFalse(String medicineCode);

    List<Medicine> findAllByIsDeletedFalseOrderByNameAsc();

    List<Medicine> findAllByIsDeletedTrueOrderByUpdatedAtDesc();

    List<Medicine> findAllByCategoryIgnoreCaseAndIsDeletedFalse(String category);

    List<Medicine> findAllByNameContainingIgnoreCaseAndIsDeletedFalse(String keyword);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m FROM Medicine m WHERE m.id = :id AND m.isDeleted = false")
    Optional<Medicine> findByIdWithLock(@Param("id") Long id);
}
