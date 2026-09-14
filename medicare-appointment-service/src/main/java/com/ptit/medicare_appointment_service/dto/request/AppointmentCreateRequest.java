package com.ptit.medicare_appointment_service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ptit.medicare_appointment_service.validator.ValidAppointmentStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentCreateRequest {

    @Size(max = 30, message = "Mã lịch hẹn không được vượt quá 30 ký tự")
    private String appointmentCode;

    @NotNull(message = "Mã bệnh nhân không được để trống")
    @Positive(message = "Mã bệnh nhân phải là số nguyên dương")
    private Long patientId;

    @NotNull(message = "Mã bác sĩ không được để trống")
    @Positive(message = "Mã bác sĩ phải là số nguyên dương")
    private Long doctorId;

    @NotNull(message = "Ngày hẹn khám không được để trống")
    @FutureOrPresent(message = "Ngày hẹn khám không thể là ngày trong quá khứ")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;

    @NotNull(message = "Giờ bắt đầu ca khám không được để trống")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @NotNull(message = "Giờ kết thúc ca khám không được để trống")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;

    @Size(max = 100, message = "Khoa phòng khám không được vượt quá 100 ký tự")
    private String department;

    @Size(max = 50, message = "Số phòng khám không được vượt quá 50 ký tự")
    private String roomNumber;

    @NotBlank(message = "Lý do khám bệnh không được để trống")
    @Size(max = 255, message = "Lý do khám bệnh không được vượt quá 255 ký tự")
    private String reason;

    @ValidAppointmentStatus
    private String status;

    @Size(max = 1000, message = "Ghi chú không được vượt quá 1000 ký tự")
    private String notes;
}
