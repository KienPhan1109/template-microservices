package com.ptit.medicare_patient_service.exception;

public class DuplicateInsuranceException extends DuplicateResourceException {
    public DuplicateInsuranceException(String insuranceId) {
        super("Mã bảo hiểm y tế '" + insuranceId + "' đã tồn tại trong hệ thống");
    }
}
