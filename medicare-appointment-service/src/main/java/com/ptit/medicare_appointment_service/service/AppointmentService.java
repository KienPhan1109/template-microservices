package com.ptit.medicare_appointment_service.service;

import com.ptit.medicare_appointment_service.dto.request.AppointmentCreateRequest;
import com.ptit.medicare_appointment_service.dto.request.AppointmentStatusUpdateRequest;
import com.ptit.medicare_appointment_service.dto.request.AppointmentUpdateRequest;
import com.ptit.medicare_appointment_service.dto.response.AppointmentResponse;
import com.ptit.medicare_appointment_service.enums.AppointmentStatus;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    List<AppointmentResponse> getAllAppointments(AppointmentStatus status, Long patientId, Long doctorId, LocalDate date);

    List<AppointmentResponse> getDeletedAppointments();

    AppointmentResponse getAppointmentById(Long id);

    AppointmentResponse getAppointmentByCode(String appointmentCode);

    List<AppointmentResponse> getAppointmentsByPatientId(Long patientId);

    List<AppointmentResponse> getAppointmentsByDoctorId(Long doctorId);

    AppointmentResponse createAppointment(AppointmentCreateRequest request);

    AppointmentResponse updateAppointment(Long id, AppointmentUpdateRequest request);

    AppointmentResponse updateAppointmentStatus(Long id, AppointmentStatusUpdateRequest request);

    AppointmentResponse cancelAppointment(Long id, String reason);

    void deleteAppointment(Long id);

    AppointmentResponse restoreAppointment(Long id);
}
