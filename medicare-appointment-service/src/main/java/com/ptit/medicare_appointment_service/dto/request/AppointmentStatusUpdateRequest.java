package com.ptit.medicare_appointment_service.dto.request;

import com.ptit.medicare_appointment_service.validator.ValidAppointmentStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentStatusUpdateRequest {

    @NotBlank(message = "Trạng thái lịch hẹn không được để trống")
    @ValidAppointmentStatus
    private String status;

    private String notes;
}
