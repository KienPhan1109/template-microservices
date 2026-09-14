package com.ptit.medicare_doctor_service.exception;

public class DuplicateLicenseException extends DuplicateResourceException {
    public DuplicateLicenseException(String licenseNumber) {
        super("Số chứng chỉ hành nghề (CCHN) đã tồn tại: " + licenseNumber);
    }
}
