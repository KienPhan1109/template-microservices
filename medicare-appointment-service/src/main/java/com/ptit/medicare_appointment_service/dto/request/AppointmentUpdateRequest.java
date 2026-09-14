package com.ptit.medicare_appointment_service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ptit.medicare_appointment_service.validator.ValidAppointmentStatus;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentUpdateRequest {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;

    @Size(max = 100, message = "Khoa phòng khám không được vượt quá 100 ký tự")
    private String department;

    @Size(max = 50, message = "Số phòng khám không được vượt quá 50 ký tự")
    private String roomNumber;

    @Size(max = 255, message = "Lý do khám bệnh không được vượt quá 255 ký tự")
    private String reason;

    @ValidAppointmentStatus
    private String status;

    @Size(max = 1000, message = "Ghi chú không được vượt quá 1000 ký tự")
    private String notes;
}
