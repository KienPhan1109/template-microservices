package com.ptit.medicare_doctor_service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DepartmentValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDepartment {
    String message() default "Chuyên khoa không hợp lệ. Vui lòng chọn chuyên khoa hợp lệ (VD: CARDIOLOGY, PEDIATRICS, DERMATOLOGY, ...)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
