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
@Constraint(validatedBy = { GenderValidator.class })
public @interface ValidGender {
    String message() default "Giới tính chỉ có thể là MALE, FEMALE, OTHER";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
