package com.ptit.medicare_pharmacy_service.mapper;

import com.ptit.medicare_pharmacy_service.dto.response.PrescriptionItemResponse;
import com.ptit.medicare_pharmacy_service.dto.response.PrescriptionResponse;
import com.ptit.medicare_pharmacy_service.entity.Prescription;
import com.ptit.medicare_pharmacy_service.entity.PrescriptionItem;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PrescriptionMapper {

    public PrescriptionItemResponse toItemResponse(PrescriptionItem item) {
        if (item == null) {
            return null;
        }

        String medicineCode = item.getMedicine() != null ? item.getMedicine().getMedicineCode() : null;
        String medicineName = item.getMedicine() != null ? item.getMedicine().getName() : null;
        String unit = item.getMedicine() != null ? item.getMedicine().getUnit() : null;

        return PrescriptionItemResponse.builder()
                .id(item.getId())
                .medicineId(item.getMedicine() != null ? item.getMedicine().getId() : null)
                .medicineCode(medicineCode)
                .medicineName(medicineName)
                .unit(unit)
                .quantity(item.getQuantity())
                .dosage(item.getDosage())
                .instruction(item.getInstruction())
                .unitPrice(item.getUnitPrice())
                .totalPrice(item.getTotalPrice())
                .build();
    }

    public PrescriptionResponse toResponse(Prescription prescription) {
        if (prescription == null) {
            return null;
        }

        List<PrescriptionItemResponse> items = (prescription.getItems() != null)
                ? prescription.getItems().stream().map(this::toItemResponse).collect(Collectors.toList())
                : Collections.emptyList();

        return PrescriptionResponse.builder()
                .id(prescription.getId())
                .prescriptionCode(prescription.getPrescriptionCode())
                .medicalId(prescription.getMedicalId())
                .patientId(prescription.getPatientId())
                .doctorId(prescription.getDoctorId())
                .totalAmount(prescription.getTotalAmount())
                .status(prescription.getStatus())
                .dispensedAt(prescription.getDispensedAt())
                .cancelledReason(prescription.getCancelledReason())
                .notes(prescription.getNotes())
                .isDeleted(prescription.getIsDeleted())
                .items(items)
                .createdAt(prescription.getCreatedAt())
                .updatedAt(prescription.getUpdatedAt())
                .build();
    }
}
