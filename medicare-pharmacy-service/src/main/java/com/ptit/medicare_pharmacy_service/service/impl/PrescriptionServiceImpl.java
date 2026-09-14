package com.ptit.medicare_pharmacy_service.service.impl;

import com.ptit.medicare_pharmacy_service.dto.request.PrescriptionCreateRequest;
import com.ptit.medicare_pharmacy_service.dto.request.PrescriptionItemRequest;
import com.ptit.medicare_pharmacy_service.dto.request.PrescriptionUpdateRequest;
import com.ptit.medicare_pharmacy_service.dto.response.PrescriptionResponse;
import com.ptit.medicare_pharmacy_service.entity.Medicine;
import com.ptit.medicare_pharmacy_service.entity.Prescription;
import com.ptit.medicare_pharmacy_service.entity.PrescriptionItem;
import com.ptit.medicare_pharmacy_service.enums.MedicineStatus;
import com.ptit.medicare_pharmacy_service.enums.PrescriptionStatus;
import com.ptit.medicare_pharmacy_service.exception.*;
import com.ptit.medicare_pharmacy_service.mapper.PrescriptionMapper;
import com.ptit.medicare_pharmacy_service.repository.MedicineRepository;
import com.ptit.medicare_pharmacy_service.repository.PrescriptionRepository;
import com.ptit.medicare_pharmacy_service.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final MedicineRepository medicineRepository;
    private final PrescriptionMapper prescriptionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getAllPrescriptions(PrescriptionStatus status, Long patientId, Long doctorId, Long medicalId) {
        List<Prescription> list;
        if (patientId != null) {
            list = prescriptionRepository.findAllByPatientIdAndIsDeletedFalseOrderByCreatedAtDesc(patientId);
        } else if (doctorId != null) {
            list = prescriptionRepository.findAllByDoctorIdAndIsDeletedFalseOrderByCreatedAtDesc(doctorId);
        } else if (medicalId != null) {
            list = prescriptionRepository.findAllByMedicalIdAndIsDeletedFalseOrderByCreatedAtDesc(medicalId);
        } else if (status != null) {
            list = prescriptionRepository.findAllByStatusAndIsDeletedFalseOrderByCreatedAtDesc(status);
        } else {
            list = prescriptionRepository.findAllByIsDeletedFalseOrderByCreatedAtDesc();
        }
        return list.stream()
                .map(prescriptionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionResponse getPrescriptionById(Long id) {
        Prescription prescription = prescriptionRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new PrescriptionNotFoundException(id));
        return prescriptionMapper.toResponse(prescription);
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionResponse getPrescriptionByCode(String code) {
        Prescription prescription = prescriptionRepository.findByPrescriptionCodeAndIsDeletedFalse(code)
                .orElseThrow(() -> new PrescriptionNotFoundException("Không tìm thấy đơn thuốc với mã: " + code));
        return prescriptionMapper.toResponse(prescription);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getPrescriptionsByPatientId(Long patientId) {
        return prescriptionRepository.findAllByPatientIdAndIsDeletedFalseOrderByCreatedAtDesc(patientId)
                .stream()
                .map(prescriptionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getPrescriptionsByMedicalId(Long medicalId) {
        return prescriptionRepository.findAllByMedicalIdAndIsDeletedFalseOrderByCreatedAtDesc(medicalId)
                .stream()
                .map(prescriptionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getDeletedPrescriptions() {
        return prescriptionRepository.findAllByIsDeletedTrueOrderByUpdatedAtDesc()
                .stream()
                .map(prescriptionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PrescriptionResponse createPrescription(PrescriptionCreateRequest request) {
        String code = request.getPrescriptionCode();
        if (code == null || code.trim().isEmpty()) {
            code = generateUniquePrescriptionCode();
            request.setPrescriptionCode(code);
        } else {
            code = code.trim();
            if (prescriptionRepository.existsByPrescriptionCode(code)) {
                throw new DuplicatePrescriptionCodeException(code);
            }
        }

        Prescription prescription = Prescription.builder()
                .prescriptionCode(code)
                .medicalId(request.getMedicalId())
                .patientId(request.getPatientId())
                .doctorId(request.getDoctorId())
                .notes(request.getNotes())
                .status(PrescriptionStatus.PENDING)
                .isDeleted(false)
                .items(new ArrayList<>())
                .build();

        if (request.getItems() != null) {
            for (PrescriptionItemRequest itemReq : request.getItems()) {
                Medicine medicine = medicineRepository.findByIdAndIsDeletedFalse(itemReq.getMedicineId())
                        .orElseThrow(() -> new MedicineNotFoundException(itemReq.getMedicineId()));

                BigDecimal unitPrice = (itemReq.getUnitPrice() != null && itemReq.getUnitPrice().compareTo(BigDecimal.ZERO) > 0)
                        ? itemReq.getUnitPrice()
                        : medicine.getUnitPrice();

                BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(itemReq.getQuantity()));

                PrescriptionItem item = PrescriptionItem.builder()
                        .prescription(prescription)
                        .medicine(medicine)
                        .quantity(itemReq.getQuantity())
                        .dosage(itemReq.getDosage().trim())
                        .instruction(itemReq.getInstruction() != null ? itemReq.getInstruction().trim() : null)
                        .unitPrice(unitPrice)
                        .totalPrice(totalPrice)
                        .build();

                prescription.addItem(item);
            }
        }

        prescription.recalculateTotalAmount();
        Prescription savedPrescription = prescriptionRepository.save(prescription);
        return prescriptionMapper.toResponse(savedPrescription);
    }

    @Override
    @Transactional
    public PrescriptionResponse updatePrescription(Long id, PrescriptionUpdateRequest request) {
        Prescription prescription = prescriptionRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new PrescriptionNotFoundException(id));

        if (prescription.getStatus() != PrescriptionStatus.PENDING) {
            throw new InvalidPrescriptionStateException("Chỉ có thể chỉnh sửa đơn thuốc khi ở trạng thái PENDING");
        }

        if (request.getNotes() != null) {
            prescription.setNotes(request.getNotes());
        }

        if (request.getItems() != null) {
            prescription.getItems().clear();
            for (PrescriptionItemRequest itemReq : request.getItems()) {
                Medicine medicine = medicineRepository.findByIdAndIsDeletedFalse(itemReq.getMedicineId())
                        .orElseThrow(() -> new MedicineNotFoundException(itemReq.getMedicineId()));

                BigDecimal unitPrice = (itemReq.getUnitPrice() != null && itemReq.getUnitPrice().compareTo(BigDecimal.ZERO) > 0)
                        ? itemReq.getUnitPrice()
                        : medicine.getUnitPrice();

                BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(itemReq.getQuantity()));

                PrescriptionItem item = PrescriptionItem.builder()
                        .prescription(prescription)
                        .medicine(medicine)
                        .quantity(itemReq.getQuantity())
                        .dosage(itemReq.getDosage().trim())
                        .instruction(itemReq.getInstruction() != null ? itemReq.getInstruction().trim() : null)
                        .unitPrice(unitPrice)
                        .totalPrice(totalPrice)
                        .build();

                prescription.addItem(item);
            }
            prescription.recalculateTotalAmount();
        }

        Prescription updatedPrescription = prescriptionRepository.save(prescription);
        return prescriptionMapper.toResponse(updatedPrescription);
    }

    @Override
    @Transactional
    public PrescriptionResponse dispensePrescription(Long id) {
        Prescription prescription = prescriptionRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new PrescriptionNotFoundException(id));

        if (prescription.getStatus() != PrescriptionStatus.PENDING) {
            throw new InvalidPrescriptionStateException(
                    "Chỉ có thể xuất thuốc cho đơn thuốc đang ở trạng thái PENDING. Trạng thái hiện tại: " + prescription.getStatus());
        }

        if (prescription.getItems() == null || prescription.getItems().isEmpty()) {
            throw new InvalidPrescriptionStateException("Đơn thuốc không có danh mục thuốc để xuất phát");
        }

        // 1. Kiểm tra tồn kho tất cả các thuốc với khóa ghi bi quan (Pessimistic Lock)
        for (PrescriptionItem item : prescription.getItems()) {
            Medicine medicine = medicineRepository.findByIdWithLock(item.getMedicine().getId())
                    .orElseThrow(() -> new MedicineNotFoundException(item.getMedicine().getId()));

            int currentStock = medicine.getStockQuantity() != null ? medicine.getStockQuantity() : 0;
            if (currentStock < item.getQuantity()) {
                throw new InsufficientStockException(medicine.getName(), item.getQuantity(), currentStock);
            }
        }

        // 2. Trừ kho thuốc sau khi đã xác thực đủ toàn bộ thuốc
        for (PrescriptionItem item : prescription.getItems()) {
            Medicine medicine = medicineRepository.findByIdWithLock(item.getMedicine().getId())
                    .orElseThrow(() -> new MedicineNotFoundException(item.getMedicine().getId()));

            int newStock = medicine.getStockQuantity() - item.getQuantity();
            medicine.setStockQuantity(newStock);
            if (newStock == 0) {
                medicine.setStatus(MedicineStatus.OUT_OF_STOCK);
            }
            medicineRepository.save(medicine);
        }

        prescription.setStatus(PrescriptionStatus.DISPENSED);
        prescription.setDispensedAt(LocalDateTime.now());
        Prescription dispensedPrescription = prescriptionRepository.save(prescription);
        return prescriptionMapper.toResponse(dispensedPrescription);
    }

    @Override
    @Transactional
    public PrescriptionResponse cancelPrescription(Long id, String reason) {
        Prescription prescription = prescriptionRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new PrescriptionNotFoundException(id));

        if (prescription.getStatus() == PrescriptionStatus.CANCELLED) {
            throw new InvalidPrescriptionStateException("Đơn thuốc này đã ở trạng thái CANCELLED");
        }

        // Nếu đơn đã phát thuốc (DISPENSED), hoàn lại tồn kho cho các thuốc
        if (prescription.getStatus() == PrescriptionStatus.DISPENSED && prescription.getItems() != null) {
            for (PrescriptionItem item : prescription.getItems()) {
                Medicine medicine = medicineRepository.findByIdWithLock(item.getMedicine().getId())
                        .orElseThrow(() -> new MedicineNotFoundException(item.getMedicine().getId()));

                int revertedStock = (medicine.getStockQuantity() != null ? medicine.getStockQuantity() : 0) + item.getQuantity();
                medicine.setStockQuantity(revertedStock);
                if (revertedStock > 0 && medicine.getStatus() == MedicineStatus.OUT_OF_STOCK) {
                    medicine.setStatus(MedicineStatus.AVAILABLE);
                }
                medicineRepository.save(medicine);
            }
        }

        prescription.setStatus(PrescriptionStatus.CANCELLED);
        prescription.setCancelledReason(reason != null && !reason.trim().isEmpty() ? reason.trim() : "Hủy đơn thuốc theo yêu cầu");
        Prescription cancelledPrescription = prescriptionRepository.save(prescription);
        return prescriptionMapper.toResponse(cancelledPrescription);
    }

    @Override
    @Transactional
    public void deletePrescription(Long id) {
        Prescription prescription = prescriptionRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new PrescriptionNotFoundException(id));
        prescription.setIsDeleted(true);
        prescriptionRepository.save(prescription);
    }

    @Override
    @Transactional
    public PrescriptionResponse restorePrescription(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new PrescriptionNotFoundException(id));

        if (Boolean.FALSE.equals(prescription.getIsDeleted())) {
            throw new IllegalArgumentException("Đơn thuốc này đang hoạt động, không cần khôi phục");
        }

        prescription.setIsDeleted(false);
        Prescription restoredPrescription = prescriptionRepository.save(prescription);
        return prescriptionMapper.toResponse(restoredPrescription);
    }

    private String generateUniquePrescriptionCode() {
        String prefix = "RX" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String code;
        do {
            int randomNum = ThreadLocalRandom.current().nextInt(1000, 9999);
            code = prefix + randomNum;
        } while (prescriptionRepository.existsByPrescriptionCode(code));
        return code;
    }
}
