package com.ptit.medicare_appointment_service.controller;

import com.ptit.medicare_appointment_service.dto.request.AppointmentCreateRequest;
import com.ptit.medicare_appointment_service.dto.request.AppointmentStatusUpdateRequest;
import com.ptit.medicare_appointment_service.dto.request.AppointmentUpdateRequest;
import com.ptit.medicare_appointment_service.dto.response.ApiResponse;
import com.ptit.medicare_appointment_service.dto.response.AppointmentResponse;
import com.ptit.medicare_appointment_service.enums.AppointmentStatus;
import com.ptit.medicare_appointment_service.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAllAppointments(
            @RequestParam(required = false) AppointmentStatus status,
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách lịch hẹn thành công",
                appointmentService.getAllAppointments(status, patientId, doctorId, date)));
    }

    @GetMapping("/deleted")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getDeletedAppointments() {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách lịch hẹn đã xóa mềm thành công",
                appointmentService.getDeletedAppointments()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> getAppointmentById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy chi tiết lịch hẹn thành công",
                appointmentService.getAppointmentById(id)));
    }

    @GetMapping("/code/{appointmentCode}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> getAppointmentByCode(@PathVariable String appointmentCode) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy chi tiết lịch hẹn theo mã thành công",
                appointmentService.getAppointmentByCode(appointmentCode)));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentsByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách lịch hẹn của bệnh nhân thành công",
                appointmentService.getAppointmentsByPatientId(patientId)));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentsByDoctorId(@PathVariable Long doctorId) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách lịch hẹn của bác sĩ thành công",
                appointmentService.getAppointmentsByDoctorId(doctorId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponse>> createAppointment(@Valid @RequestBody AppointmentCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Đặt lịch hẹn khám bệnh mới thành công",
                        appointmentService.createAppointment(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Cập nhật thông tin lịch hẹn thành công",
                appointmentService.updateAppointment(id, request)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<AppointmentResponse>> updateAppointmentStatus(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentStatusUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Cập nhật trạng thái lịch hẹn thành công",
                appointmentService.updateAppointmentStatus(id, request)));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<AppointmentResponse>> cancelAppointment(
            @PathVariable Long id,
            @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(ApiResponse.ok("Hủy lịch hẹn thành công",
                appointmentService.cancelAppointment(id, reason)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok(ApiResponse.ok("Xóa mềm lịch hẹn thành công"));
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<AppointmentResponse>> restoreAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Khôi phục lịch hẹn thành công",
                appointmentService.restoreAppointment(id)));
    }
}
