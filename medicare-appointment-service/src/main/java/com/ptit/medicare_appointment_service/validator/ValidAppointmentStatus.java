package com.ptit.medicare_appointment_service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidAppointmentStatusValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAppointmentStatus {
    String message() default "Trạng thái lịch hẹn không hợp lệ (hỗ trợ: SCHEDULED, CONFIRMED, CHECKED_IN, IN_PROGRESS, COMPLETED, CANCELLED, NO_SHOW)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
