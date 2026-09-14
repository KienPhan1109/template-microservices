package com.ptit.medicare_doctor_service.controller;

import com.ptit.medicare_doctor_service.dto.request.DepartmentCreateRequest;
import com.ptit.medicare_doctor_service.dto.request.DepartmentUpdateRequest;
import com.ptit.medicare_doctor_service.dto.response.ApiResponse;
import com.ptit.medicare_doctor_service.dto.response.DepartmentResponse;
import com.ptit.medicare_doctor_service.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAllDepartments() {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách chuyên khoa thành công", departmentService.getAllDepartments()));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getActiveDepartments() {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách chuyên khoa đang hoạt động thành công", departmentService.getActiveDepartments()));
    }

    @GetMapping("/deleted")
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getDeletedDepartments() {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách chuyên khoa đã xóa mềm thành công", departmentService.getDeletedDepartments()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy thông tin chuyên khoa thành công", departmentService.getDepartmentById(id)));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartmentByCode(@PathVariable String code) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy thông tin chuyên khoa theo mã thành công", departmentService.getDepartmentByCode(code)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponse>> createDepartment(@Valid @RequestBody DepartmentCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Tạo mới chuyên khoa thành công", departmentService.createDepartment(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Cập nhật chuyên khoa thành công", departmentService.updateDepartment(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok(ApiResponse.ok("Xóa mềm chuyên khoa thành công"));
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<DepartmentResponse>> restoreDepartment(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Khôi phục chuyên khoa thành công", departmentService.restoreDepartment(id)));
    }
}
