package com.ptit.medicare_pharmacy_service.repository;

import com.ptit.medicare_pharmacy_service.entity.PrescriptionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, Long> {

    List<PrescriptionItem> findAllByPrescriptionId(Long prescriptionId);

    void deleteAllByPrescriptionId(Long prescriptionId);
}
