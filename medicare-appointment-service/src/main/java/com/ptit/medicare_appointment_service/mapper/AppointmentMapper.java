package com.ptit.medicare_appointment_service.mapper;

import com.ptit.medicare_appointment_service.dto.request.AppointmentCreateRequest;
import com.ptit.medicare_appointment_service.dto.request.AppointmentUpdateRequest;
import com.ptit.medicare_appointment_service.dto.response.AppointmentResponse;
import com.ptit.medicare_appointment_service.entity.Appointment;
import com.ptit.medicare_appointment_service.enums.AppointmentStatus;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public Appointment toEntity(AppointmentCreateRequest request) {
        AppointmentStatus status = AppointmentStatus.SCHEDULED;
        if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            status = AppointmentStatus.fromString(request.getStatus());
        }

        return Appointment.builder()
                .appointmentCode(request.getAppointmentCode())
                .patientId(request.getPatientId())
                .doctorId(request.getDoctorId())
                .appointmentDate(request.getAppointmentDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .department(request.getDepartment())
                .roomNumber(request.getRoomNumber())
                .reason(request.getReason())
                .status(status)
                .notes(request.getNotes())
                .isDeleted(false)
                .build();
    }

    public void updateEntityFromRequest(Appointment appointment, AppointmentUpdateRequest request) {
        if (request.getAppointmentDate() != null) {
            appointment.setAppointmentDate(request.getAppointmentDate());
        }
        if (request.getStartTime() != null) {
            appointment.setStartTime(request.getStartTime());
        }
        if (request.getEndTime() != null) {
            appointment.setEndTime(request.getEndTime());
        }
        if (request.getDepartment() != null) {
            appointment.setDepartment(request.getDepartment());
        }
        if (request.getRoomNumber() != null) {
            appointment.setRoomNumber(request.getRoomNumber());
        }
        if (request.getReason() != null && !request.getReason().trim().isEmpty()) {
            appointment.setReason(request.getReason());
        }
        if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            appointment.setStatus(AppointmentStatus.fromString(request.getStatus()));
        }
        if (request.getNotes() != null) {
            appointment.setNotes(request.getNotes());
        }
    }

    public AppointmentResponse toResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .appointmentCode(appointment.getAppointmentCode())
                .patientId(appointment.getPatientId())
                .doctorId(appointment.getDoctorId())
                .appointmentDate(appointment.getAppointmentDate())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .department(appointment.getDepartment())
                .roomNumber(appointment.getRoomNumber())
                .reason(appointment.getReason())
                .status(appointment.getStatus())
                .statusDescription(appointment.getStatus() != null ? appointment.getStatus().getDescription() : null)
                .notes(appointment.getNotes())
                .isDeleted(appointment.isDeleted())
                .createdAt(appointment.getCreatedAt())
                .updatedAt(appointment.getUpdatedAt())
                .build();
    }
}
