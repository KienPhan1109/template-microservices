package com.ptit.medicare_pharmacy_service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueMedicineCodeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueMedicineCode {
    String message() default "Mã thuốc đã tồn tại trong danh mục dược phẩm";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
