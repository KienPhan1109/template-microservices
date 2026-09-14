package com.ptit.medicare_doctor_service.controller;

import com.ptit.medicare_doctor_service.dto.request.DoctorCreateRequest;
import com.ptit.medicare_doctor_service.dto.request.DoctorFilterRequest;
import com.ptit.medicare_doctor_service.dto.response.ApiResponse;
import com.ptit.medicare_doctor_service.dto.response.DoctorResponse;
import com.ptit.medicare_doctor_service.enums.DoctorGender;
import com.ptit.medicare_doctor_service.enums.DoctorStatus;
import com.ptit.medicare_doctor_service.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getDoctors(
            @RequestParam(name = "departmentId", required = false) Long departmentId,
            @RequestParam(name = "department", required = false) String department,
            @RequestParam(name = "status", required = false) DoctorStatus status,
            @RequestParam(name = "gender", required = false) DoctorGender gender,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "minExperience", required = false) Integer minExperience,
            @RequestParam(name = "maxFee", required = false) BigDecimal maxFee) {

        DoctorFilterRequest filter = DoctorFilterRequest.builder()
                .departmentId(departmentId)
                .department(department)
                .status(status)
                .gender(gender)
                .keyword(keyword)
                .minExperience(minExperience)
                .maxFee(maxFee)
                .build();

        return ResponseEntity.ok(ApiResponse.ok(
                "Lấy danh sách bác sĩ thành công",
                doctorService.getDoctors(filter)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponse>> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy thông tin bác sĩ thành công", doctorService.getDoctorById(id)));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getDoctorsByDepartmentId(@PathVariable Long departmentId) {
        return ResponseEntity.ok(ApiResponse.ok(
                "Lấy danh sách bác sĩ theo ID chuyên khoa thành công",
                doctorService.getDoctorsByDepartmentId(departmentId)
        ));
    }

    @GetMapping("/deleted")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getDeletedDoctors() {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách bác sĩ đã xóa mềm thành công", doctorService.getDeletedDoctors()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DoctorResponse>> createDoctor(@Valid @RequestBody DoctorCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Tạo mới bác sĩ thành công", doctorService.createDoctor(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok(ApiResponse.ok("Xóa mềm bác sĩ thành công"));
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<DoctorResponse>> restoreDoctor(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Khôi phục bác sĩ thành công", doctorService.restoreDoctor(id)));
    }
}
