package com.ptit.medicare_appointment_service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ptit.medicare_appointment_service.enums.AppointmentStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponse {
    private Long id;
    private String appointmentCode;
    private Long patientId;
    private Long doctorId;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate appointmentDate;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    private String department;
    private String roomNumber;
    private String reason;
    private AppointmentStatus status;
    private String statusDescription;
    private String notes;
    private boolean isDeleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
