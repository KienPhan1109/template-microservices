package com.ptit.medicare_patient_service.exception;

public class DuplicateEmailException extends DuplicateResourceException {
    public DuplicateEmailException(String email) {
        super("Email '" + email + "' đã tồn tại trong hệ thống");
    }
}
