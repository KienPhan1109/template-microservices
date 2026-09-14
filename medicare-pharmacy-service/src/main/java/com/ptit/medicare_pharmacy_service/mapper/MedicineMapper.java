package com.ptit.medicare_pharmacy_service.mapper;

import com.ptit.medicare_pharmacy_service.dto.request.MedicineCreateRequest;
import com.ptit.medicare_pharmacy_service.dto.request.MedicineUpdateRequest;
import com.ptit.medicare_pharmacy_service.dto.response.MedicineResponse;
import com.ptit.medicare_pharmacy_service.entity.Medicine;
import com.ptit.medicare_pharmacy_service.enums.MedicineStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MedicineMapper {

    public Medicine toEntity(MedicineCreateRequest request) {
        if (request == null) {
            return null;
        }

        MedicineStatus status = (request.getStockQuantity() != null && request.getStockQuantity() > 0)
                ? MedicineStatus.AVAILABLE
                : MedicineStatus.OUT_OF_STOCK;

        if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            status = MedicineStatus.valueOf(request.getStatus().trim().toUpperCase());
        }

        return Medicine.builder()
                .medicineCode(request.getMedicineCode() != null ? request.getMedicineCode().trim() : null)
                .registrationNumber(request.getRegistrationNumber() != null ? request.getRegistrationNumber().trim() : null)
                .name(request.getName() != null ? request.getName().trim() : null)
                .activeIngredient(request.getActiveIngredient() != null ? request.getActiveIngredient().trim() : null)
                .category(request.getCategory() != null ? request.getCategory().trim() : null)
                .dosageForm(request.getDosageForm() != null ? request.getDosageForm().trim() : null)
                .unit(request.getUnit() != null ? request.getUnit().trim() : null)
                .unitPrice(request.getUnitPrice() != null ? request.getUnitPrice() : BigDecimal.ZERO)
                .stockQuantity(request.getStockQuantity() != null ? request.getStockQuantity() : 0)
                .expiryDate(request.getExpiryDate())
                .status(status)
                .isDeleted(false)
                .build();
    }

    public MedicineResponse toResponse(Medicine medicine) {
        if (medicine == null) {
            return null;
        }

        return MedicineResponse.builder()
                .id(medicine.getId())
                .medicineCode(medicine.getMedicineCode())
                .registrationNumber(medicine.getRegistrationNumber())
                .name(medicine.getName())
                .activeIngredient(medicine.getActiveIngredient())
                .category(medicine.getCategory())
                .dosageForm(medicine.getDosageForm())
                .unit(medicine.getUnit())
                .unitPrice(medicine.getUnitPrice())
                .stockQuantity(medicine.getStockQuantity())
                .expiryDate(medicine.getExpiryDate())
                .status(medicine.getStatus())
                .isDeleted(medicine.getIsDeleted())
                .createdAt(medicine.getCreatedAt())
                .updatedAt(medicine.getUpdatedAt())
                .build();
    }

    public void updateEntity(Medicine medicine, MedicineUpdateRequest request) {
        if (request == null || medicine == null) {
            return;
        }

        if (request.getRegistrationNumber() != null) {
            medicine.setRegistrationNumber(request.getRegistrationNumber().trim());
        }
        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            medicine.setName(request.getName().trim());
        }
        if (request.getActiveIngredient() != null) {
            medicine.setActiveIngredient(request.getActiveIngredient().trim());
        }
        if (request.getCategory() != null) {
            medicine.setCategory(request.getCategory().trim());
        }
        if (request.getDosageForm() != null) {
            medicine.setDosageForm(request.getDosageForm().trim());
        }
        if (request.getUnit() != null && !request.getUnit().trim().isEmpty()) {
            medicine.setUnit(request.getUnit().trim());
        }
        if (request.getUnitPrice() != null) {
            medicine.setUnitPrice(request.getUnitPrice());
        }
        if (request.getStockQuantity() != null) {
            medicine.setStockQuantity(request.getStockQuantity());
            if (medicine.getStockQuantity() == 0 && medicine.getStatus() == MedicineStatus.AVAILABLE) {
                medicine.setStatus(MedicineStatus.OUT_OF_STOCK);
            } else if (medicine.getStockQuantity() > 0 && medicine.getStatus() == MedicineStatus.OUT_OF_STOCK) {
                medicine.setStatus(MedicineStatus.AVAILABLE);
            }
        }
        if (request.getExpiryDate() != null) {
            medicine.setExpiryDate(request.getExpiryDate());
        }
        if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            medicine.setStatus(MedicineStatus.valueOf(request.getStatus().trim().toUpperCase()));
        }
    }
}
