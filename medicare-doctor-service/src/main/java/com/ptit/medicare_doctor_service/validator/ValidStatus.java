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
@Constraint(validatedBy = { StatusValidator.class })
public @interface ValidStatus {
    String message() default "Trạng thái chỉ có thể là ACTIVE, ON_LEAVE, RESIGNED";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
