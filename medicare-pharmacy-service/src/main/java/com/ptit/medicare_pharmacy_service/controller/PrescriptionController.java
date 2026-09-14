package com.ptit.medicare_pharmacy_service.controller;

import com.ptit.medicare_pharmacy_service.dto.request.PrescriptionCreateRequest;
import com.ptit.medicare_pharmacy_service.dto.request.PrescriptionUpdateRequest;
import com.ptit.medicare_pharmacy_service.dto.response.ApiResponse;
import com.ptit.medicare_pharmacy_service.dto.response.PrescriptionResponse;
import com.ptit.medicare_pharmacy_service.enums.PrescriptionStatus;
import com.ptit.medicare_pharmacy_service.service.PrescriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PrescriptionResponse>>> getAllPrescriptions(
            @RequestParam(required = false) PrescriptionStatus status,
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) Long medicalId) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách đơn thuốc thành công",
                prescriptionService.getAllPrescriptions(status, patientId, doctorId, medicalId)));
    }

    @GetMapping("/deleted")
    public ResponseEntity<ApiResponse<List<PrescriptionResponse>>> getDeletedPrescriptions() {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách đơn thuốc đã xóa mềm thành công",
                prescriptionService.getDeletedPrescriptions()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> getPrescriptionById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy chi tiết đơn thuốc thành công",
                prescriptionService.getPrescriptionById(id)));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> getPrescriptionByCode(@PathVariable String code) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy chi tiết đơn thuốc theo mã thành công",
                prescriptionService.getPrescriptionByCode(code)));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<List<PrescriptionResponse>>> getPrescriptionsByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách đơn thuốc của bệnh nhân thành công",
                prescriptionService.getPrescriptionsByPatientId(patientId)));
    }

    @GetMapping("/medical/{medicalId}")
    public ResponseEntity<ApiResponse<List<PrescriptionResponse>>> getPrescriptionsByMedicalId(@PathVariable Long medicalId) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách đơn thuốc theo hồ sơ bệnh án thành công",
                prescriptionService.getPrescriptionsByMedicalId(medicalId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PrescriptionResponse>> createPrescription(@Valid @RequestBody PrescriptionCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Kê đơn thuốc mới thành công",
                        prescriptionService.createPrescription(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> updatePrescription(
            @PathVariable Long id,
            @Valid @RequestBody PrescriptionUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Cập nhật đơn thuốc thành công",
                prescriptionService.updatePrescription(id, request)));
    }

    @PostMapping("/{id}/dispense")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> dispensePrescription(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Xuất phát thuốc thành công",
                prescriptionService.dispensePrescription(id)));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> cancelPrescription(
            @PathVariable Long id,
            @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(ApiResponse.ok("Hủy đơn thuốc thành công",
                prescriptionService.cancelPrescription(id, reason)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntity.ok(ApiResponse.ok("Xóa mềm đơn thuốc thành công"));
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> restorePrescription(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Khôi phục đơn thuốc thành công",
                prescriptionService.restorePrescription(id)));
    }
}
