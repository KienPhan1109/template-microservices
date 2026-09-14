package com.ptit.medicare_pharmacy_service.controller;

import com.ptit.medicare_pharmacy_service.dto.request.MedicineCreateRequest;
import com.ptit.medicare_pharmacy_service.dto.request.MedicineStockUpdateRequest;
import com.ptit.medicare_pharmacy_service.dto.request.MedicineUpdateRequest;
import com.ptit.medicare_pharmacy_service.dto.response.ApiResponse;
import com.ptit.medicare_pharmacy_service.dto.response.MedicineResponse;
import com.ptit.medicare_pharmacy_service.service.MedicineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MedicineResponse>>> getAllMedicines(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách thuốc thành công",
                medicineService.getAllMedicines(category, keyword)));
    }

    @GetMapping("/deleted")
    public ResponseEntity<ApiResponse<List<MedicineResponse>>> getDeletedMedicines() {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách thuốc đã xóa mềm thành công",
                medicineService.getDeletedMedicines()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MedicineResponse>> getMedicineById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy thông tin chi tiết thuốc thành công",
                medicineService.getMedicineById(id)));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<MedicineResponse>> getMedicineByCode(@PathVariable String code) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy thông tin thuốc theo mã thành công",
                medicineService.getMedicineByCode(code)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MedicineResponse>> createMedicine(@Valid @RequestBody MedicineCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Thêm mới thuốc vào danh mục thành công",
                        medicineService.createMedicine(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MedicineResponse>> updateMedicine(
            @PathVariable Long id,
            @Valid @RequestBody MedicineUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Cập nhật thông tin thuốc thành công",
                medicineService.updateMedicine(id, request)));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<MedicineResponse>> updateStock(
            @PathVariable Long id,
            @Valid @RequestBody MedicineStockUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Cập nhật số lượng tồn kho thành công",
                medicineService.updateStock(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return ResponseEntity.ok(ApiResponse.ok("Xóa mềm thuốc thành công"));
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<MedicineResponse>> restoreMedicine(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Khôi phục thuốc thành công",
                medicineService.restoreMedicine(id)));
    }
}
