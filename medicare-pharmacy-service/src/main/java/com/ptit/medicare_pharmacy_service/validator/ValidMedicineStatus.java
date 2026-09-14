package com.ptit.medicare_pharmacy_service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MedicineStatusValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMedicineStatus {
    String message() default "Trạng thái thuốc chỉ có thể là AVAILABLE, OUT_OF_STOCK, DISCONTINUED";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
