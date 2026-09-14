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
@Constraint(validatedBy = { UniqueInsuranceIdValidator.class })
public @interface UniqueInsuranceId {
    String message() default "Mã bảo hiểm y tế đã tồn tại trong hệ thống";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
