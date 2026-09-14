package com.ptit.medicare_appointment_service.service.impl;

import com.ptit.medicare_appointment_service.dto.request.AppointmentCreateRequest;
import com.ptit.medicare_appointment_service.dto.request.AppointmentStatusUpdateRequest;
import com.ptit.medicare_appointment_service.dto.request.AppointmentUpdateRequest;
import com.ptit.medicare_appointment_service.dto.response.AppointmentResponse;
import com.ptit.medicare_appointment_service.entity.Appointment;
import com.ptit.medicare_appointment_service.enums.AppointmentStatus;
import com.ptit.medicare_appointment_service.exception.AppointmentNotFoundException;
import com.ptit.medicare_appointment_service.exception.AppointmentTimeConflictException;
import com.ptit.medicare_appointment_service.exception.DuplicateAppointmentException;
import com.ptit.medicare_appointment_service.mapper.AppointmentMapper;
import com.ptit.medicare_appointment_service.repository.AppointmentRepository;
import com.ptit.medicare_appointment_service.service.AppointmentService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAllAppointments(AppointmentStatus status, Long patientId, Long doctorId, LocalDate date) {
        Specification<Appointment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.isFalse(root.get("isDeleted")));

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (patientId != null) {
                predicates.add(cb.equal(root.get("patientId"), patientId));
            }
            if (doctorId != null) {
                predicates.add(cb.equal(root.get("doctorId"), doctorId));
            }
            if (date != null) {
                predicates.add(cb.equal(root.get("appointmentDate"), date));
            }

            query.orderBy(cb.desc(root.get("appointmentDate")), cb.asc(root.get("startTime")));
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return appointmentRepository.findAll(spec).stream()
                .map(appointmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getDeletedAppointments() {
        return appointmentRepository.findAllByIsDeletedTrue().stream()
                .map(appointmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
        return appointmentMapper.toResponse(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentByCode(String appointmentCode) {
        Appointment appointment = appointmentRepository.findByAppointmentCodeAndIsDeletedFalse(appointmentCode.trim())
                .orElseThrow(() -> new AppointmentNotFoundException("Không tìm thấy lịch hẹn với mã: " + appointmentCode));
        return appointmentMapper.toResponse(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findAllByPatientIdAndIsDeletedFalse(patientId).stream()
                .map(appointmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findAllByDoctorIdAndIsDeletedFalse(doctorId).stream()
                .map(appointmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentResponse createAppointment(AppointmentCreateRequest request) {
        // 1. Kiểm tra thời gian bắt đầu và kết thúc
        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new IllegalArgumentException("Giờ kết thúc ca khám (" + request.getEndTime() + ") phải sau giờ bắt đầu (" + request.getStartTime() + ")");
        }

        // 2. Tự động sinh mã nếu chưa có hoặc kiểm tra trùng mã
        String code = request.getAppointmentCode();
        if (code == null || code.trim().isEmpty()) {
            code = generateAppointmentCode();
            request.setAppointmentCode(code);
        } else {
            code = code.trim().toUpperCase();
            if (appointmentRepository.existsByAppointmentCode(code)) {
                throw new DuplicateAppointmentException("Mã lịch hẹn '" + code + "' đã tồn tại trong hệ thống");
            }
            request.setAppointmentCode(code);
        }

        // 3. Kiểm tra xung đột lịch khám của bác sĩ (chống Double-booking)
        if (appointmentRepository.hasDoctorOverlap(request.getDoctorId(), request.getAppointmentDate(), request.getStartTime(), request.getEndTime(), null)) {
            throw new AppointmentTimeConflictException(
                    String.format("Bác sĩ ID %d đã có lịch hẹn khác trùng khung giờ %s - %s ngày %s",
                            request.getDoctorId(), request.getStartTime(), request.getEndTime(), request.getAppointmentDate())
            );
        }

        // 4. Kiểm tra xung đột lịch khám của bệnh nhân
        if (appointmentRepository.hasPatientOverlap(request.getPatientId(), request.getAppointmentDate(), request.getStartTime(), request.getEndTime(), null)) {
            throw new AppointmentTimeConflictException(
                    String.format("Bệnh nhân ID %d đã có lịch hẹn khác trùng khung giờ %s - %s ngày %s",
                            request.getPatientId(), request.getStartTime(), request.getEndTime(), request.getAppointmentDate())
            );
        }

        Appointment appointment = appointmentMapper.toEntity(request);
        Appointment saved = appointmentRepository.save(appointment);
        log.info("Created appointment with ID {} and code {}", saved.getId(), saved.getAppointmentCode());
        return appointmentMapper.toResponse(saved);
    }

    @Override
    public AppointmentResponse updateAppointment(Long id, AppointmentUpdateRequest request) {
        Appointment appointment = appointmentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        LocalDate targetDate = request.getAppointmentDate() != null ? request.getAppointmentDate() : appointment.getAppointmentDate();
        LocalTime targetStart = request.getStartTime() != null ? request.getStartTime() : appointment.getStartTime();
        LocalTime targetEnd = request.getEndTime() != null ? request.getEndTime() : appointment.getEndTime();

        if (!targetEnd.isAfter(targetStart)) {
            throw new IllegalArgumentException("Giờ kết thúc ca khám (" + targetEnd + ") phải sau giờ bắt đầu (" + targetStart + ")");
        }

        // Kiểm tra xung đột lịch khám nếu có đổi ngày hoặc giờ
        if (appointmentRepository.hasDoctorOverlap(appointment.getDoctorId(), targetDate, targetStart, targetEnd, id)) {
            throw new AppointmentTimeConflictException(
                    String.format("Bác sĩ ID %d đã có lịch hẹn khác trùng khung giờ %s - %s ngày %s",
                            appointment.getDoctorId(), targetStart, targetEnd, targetDate)
            );
        }

        if (appointmentRepository.hasPatientOverlap(appointment.getPatientId(), targetDate, targetStart, targetEnd, id)) {
            throw new AppointmentTimeConflictException(
                    String.format("Bệnh nhân ID %d đã có lịch hẹn khác trùng khung giờ %s - %s ngày %s",
                            appointment.getPatientId(), targetStart, targetEnd, targetDate)
            );
        }

        appointmentMapper.updateEntityFromRequest(appointment, request);
        Appointment updated = appointmentRepository.save(appointment);
        log.info("Updated appointment ID {}", updated.getId());
        return appointmentMapper.toResponse(updated);
    }

    @Override
    public AppointmentResponse updateAppointmentStatus(Long id, AppointmentStatusUpdateRequest request) {
        Appointment appointment = appointmentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        AppointmentStatus newStatus = AppointmentStatus.fromString(request.getStatus());
        appointment.setStatus(newStatus);
        if (request.getNotes() != null && !request.getNotes().trim().isEmpty()) {
            String currentNotes = appointment.getNotes() != null ? appointment.getNotes() + " | " : "";
            appointment.setNotes(currentNotes + request.getNotes().trim());
        }

        Appointment updated = appointmentRepository.save(appointment);
        log.info("Updated status for appointment ID {} to {}", id, newStatus);
        return appointmentMapper.toResponse(updated);
    }

    @Override
    public AppointmentResponse cancelAppointment(Long id, String reason) {
        Appointment appointment = appointmentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        appointment.setStatus(AppointmentStatus.CANCELLED);
        String cancelNote = "[ĐÃ HỦY]";
        if (reason != null && !reason.trim().isEmpty()) {
            cancelNote += " Lý do: " + reason.trim();
        }
        String currentNotes = appointment.getNotes() != null ? appointment.getNotes() + " | " : "";
        appointment.setNotes(currentNotes + cancelNote);

        Appointment updated = appointmentRepository.save(appointment);
        log.info("Cancelled appointment ID {} with reason: {}", id, reason);
        return appointmentMapper.toResponse(updated);
    }

    @Override
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
        appointment.setDeleted(true);
        appointmentRepository.save(appointment);
        log.info("Soft-deleted appointment ID {}", id);
    }

    @Override
    public AppointmentResponse restoreAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        if (!appointment.isDeleted()) {
            throw new IllegalArgumentException("Lịch hẹn ID " + id + " hiện chưa bị xóa mềm");
        }

        appointment.setDeleted(false);
        Appointment restored = appointmentRepository.save(appointment);
        log.info("Restored appointment ID {}", id);
        return appointmentMapper.toResponse(restored);
    }

    private String generateAppointmentCode() {
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String code;
        int attempts = 0;
        do {
            int randomNum = 1000 + new Random().nextInt(9000);
            code = "APT" + dateStr + randomNum;
            attempts++;
            if (attempts > 50) {
                code = "APT" + System.currentTimeMillis();
                break;
            }
        } while (appointmentRepository.existsByAppointmentCode(code));
        return code;
    }
}
