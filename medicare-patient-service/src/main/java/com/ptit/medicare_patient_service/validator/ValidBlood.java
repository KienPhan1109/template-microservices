package com.ptit.medicare_patient_service.validator;

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
@Constraint(validatedBy = { BloodValidator.class })
public @interface ValidBlood {
    String message() default "Nhóm máu chỉ có thể là A, B, AB, O, UNKNOWN";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
