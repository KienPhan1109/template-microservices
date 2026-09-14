package com.ptit.medicare_patient_service.controller;

import com.ptit.medicare_patient_service.dto.request.PatientCreateRequest;
import com.ptit.medicare_patient_service.dto.response.ApiResponse;
import com.ptit.medicare_patient_service.dto.response.PatientResponse;
import com.ptit.medicare_patient_service.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patients")
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PatientResponse>>> getAllPatients() {
        return ResponseEntity.ok(ApiResponse.ok("Lấy toàn bộ bệnh nhân thành công", patientService.getAllPatients()));
    }

    @GetMapping("/deleted")
    public ResponseEntity<ApiResponse<List<PatientResponse>>> getDeletedPatients() {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách bệnh nhân đã xóa mềm thành công", patientService.getDeletedPatients()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy thông tin bệnh nhân thành công", patientService.getPatientById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PatientResponse>> createPatient(@Valid @RequestBody PatientCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Tạo mới bệnh nhân thành công", patientService.createPatient(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok(ApiResponse.ok("Xóa mềm bệnh nhân thành công"));
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<PatientResponse>> restorePatient(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Khôi phục bệnh nhân thành công", patientService.restorePatient(id)));
    }
}
