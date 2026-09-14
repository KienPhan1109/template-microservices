package com.ptit.medicare_pharmacy_service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniquePrescriptionCodeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePrescriptionCode {
    String message() default "Mã đơn thuốc đã tồn tại trên hệ thống";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
