package com.ptit.medicare_pharmacy_service.service;

import com.ptit.medicare_pharmacy_service.dto.request.MedicineCreateRequest;
import com.ptit.medicare_pharmacy_service.dto.request.MedicineStockUpdateRequest;
import com.ptit.medicare_pharmacy_service.dto.request.MedicineUpdateRequest;
import com.ptit.medicare_pharmacy_service.dto.response.MedicineResponse;

import java.util.List;

public interface MedicineService {

    List<MedicineResponse> getAllMedicines(String category, String keyword);

    MedicineResponse getMedicineById(Long id);

    MedicineResponse getMedicineByCode(String code);

    List<MedicineResponse> getDeletedMedicines();

    MedicineResponse createMedicine(MedicineCreateRequest request);

    MedicineResponse updateMedicine(Long id, MedicineUpdateRequest request);

    MedicineResponse updateStock(Long id, MedicineStockUpdateRequest request);

    void deleteMedicine(Long id);

    MedicineResponse restoreMedicine(Long id);
}
