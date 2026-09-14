package com.ptit.medicare_doctor_service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { DepartmentStatusValidator.class })
public @interface ValidDepartmentStatus {
    String message() default "Trạng thái chuyên khoa chỉ có thể là ACTIVE hoặc INACTIVE";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
