package com.ptit.medicare_pharmacy_service.service.impl;

import com.ptit.medicare_pharmacy_service.dto.request.MedicineCreateRequest;
import com.ptit.medicare_pharmacy_service.dto.request.MedicineStockUpdateRequest;
import com.ptit.medicare_pharmacy_service.dto.request.MedicineUpdateRequest;
import com.ptit.medicare_pharmacy_service.dto.response.MedicineResponse;
import com.ptit.medicare_pharmacy_service.entity.Medicine;
import com.ptit.medicare_pharmacy_service.enums.MedicineStatus;
import com.ptit.medicare_pharmacy_service.exception.DuplicateMedicineCodeException;
import com.ptit.medicare_pharmacy_service.exception.InsufficientStockException;
import com.ptit.medicare_pharmacy_service.exception.MedicineNotFoundException;
import com.ptit.medicare_pharmacy_service.mapper.MedicineMapper;
import com.ptit.medicare_pharmacy_service.repository.MedicineRepository;
import com.ptit.medicare_pharmacy_service.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;
    private final MedicineMapper medicineMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MedicineResponse> getAllMedicines(String category, String keyword) {
        List<Medicine> medicines;
        if (category != null && !category.trim().isEmpty()) {
            medicines = medicineRepository.findAllByCategoryIgnoreCaseAndIsDeletedFalse(category.trim());
        } else if (keyword != null && !keyword.trim().isEmpty()) {
            medicines = medicineRepository.findAllByNameContainingIgnoreCaseAndIsDeletedFalse(keyword.trim());
        } else {
            medicines = medicineRepository.findAllByIsDeletedFalseOrderByNameAsc();
        }
        return medicines.stream()
                .map(medicineMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MedicineResponse getMedicineById(Long id) {
        Medicine medicine = medicineRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new MedicineNotFoundException(id));
        return medicineMapper.toResponse(medicine);
    }

    @Override
    @Transactional(readOnly = true)
    public MedicineResponse getMedicineByCode(String code) {
        Medicine medicine = medicineRepository.findByMedicineCodeAndIsDeletedFalse(code)
                .orElseThrow(() -> new MedicineNotFoundException("Không tìm thấy thuốc với mã: " + code));
        return medicineMapper.toResponse(medicine);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicineResponse> getDeletedMedicines() {
        return medicineRepository.findAllByIsDeletedTrueOrderByUpdatedAtDesc()
                .stream()
                .map(medicineMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MedicineResponse createMedicine(MedicineCreateRequest request) {
        String code = request.getMedicineCode();
        if (code == null || code.trim().isEmpty()) {
            code = generateUniqueMedicineCode();
            request.setMedicineCode(code);
        } else {
            code = code.trim();
            if (medicineRepository.existsByMedicineCode(code)) {
                throw new DuplicateMedicineCodeException(code);
            }
        }

        Medicine medicine = medicineMapper.toEntity(request);
        Medicine savedMedicine = medicineRepository.save(medicine);
        return medicineMapper.toResponse(savedMedicine);
    }

    @Override
    @Transactional
    public MedicineResponse updateMedicine(Long id, MedicineUpdateRequest request) {
        Medicine medicine = medicineRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new MedicineNotFoundException(id));

        medicineMapper.updateEntity(medicine, request);
        Medicine updatedMedicine = medicineRepository.save(medicine);
        return medicineMapper.toResponse(updatedMedicine);
    }

    @Override
    @Transactional
    public MedicineResponse updateStock(Long id, MedicineStockUpdateRequest request) {
        Medicine medicine = medicineRepository.findByIdWithLock(id)
                .orElseThrow(() -> new MedicineNotFoundException(id));

        int change = request.getQuantity() != null ? Math.abs(request.getQuantity()) : 0;
        String type = request.getType() != null ? request.getType().trim().toUpperCase() : "IMPORT";

        int currentStock = medicine.getStockQuantity() != null ? medicine.getStockQuantity() : 0;
        int newStock;

        switch (type) {
            case "IMPORT":
                newStock = currentStock + change;
                break;
            case "EXPORT":
                if (currentStock < change) {
                    throw new InsufficientStockException(medicine.getName(), change, currentStock);
                }
                newStock = currentStock - change;
                break;
            case "SET":
                newStock = change;
                break;
            default:
                throw new IllegalArgumentException("Loại điều chỉnh kho không hợp lệ: " + type);
        }

        medicine.setStockQuantity(newStock);
        if (newStock == 0 && medicine.getStatus() == MedicineStatus.AVAILABLE) {
            medicine.setStatus(MedicineStatus.OUT_OF_STOCK);
        } else if (newStock > 0 && medicine.getStatus() == MedicineStatus.OUT_OF_STOCK) {
            medicine.setStatus(MedicineStatus.AVAILABLE);
        }

        Medicine savedMedicine = medicineRepository.save(medicine);
        return medicineMapper.toResponse(savedMedicine);
    }

    @Override
    @Transactional
    public void deleteMedicine(Long id) {
        Medicine medicine = medicineRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new MedicineNotFoundException(id));
        medicine.setIsDeleted(true);
        medicineRepository.save(medicine);
    }

    @Override
    @Transactional
    public MedicineResponse restoreMedicine(Long id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new MedicineNotFoundException(id));

        if (Boolean.FALSE.equals(medicine.getIsDeleted())) {
            throw new IllegalArgumentException("Thuốc này đang hoạt động, không cần khôi phục");
        }

        medicine.setIsDeleted(false);
        Medicine restoredMedicine = medicineRepository.save(medicine);
        return medicineMapper.toResponse(restoredMedicine);
    }

    private String generateUniqueMedicineCode() {
        String prefix = "MED-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM"));
        String code;
        do {
            int randomNum = ThreadLocalRandom.current().nextInt(1000, 9999);
            code = prefix + "-" + randomNum;
        } while (medicineRepository.existsByMedicineCode(code));
        return code;
    }
}
