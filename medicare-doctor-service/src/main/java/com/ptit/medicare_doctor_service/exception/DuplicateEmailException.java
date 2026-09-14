package com.ptit.medicare_doctor_service.exception;

public class DuplicateEmailException extends DuplicateResourceException {
    public DuplicateEmailException(String email) {
        super("Email đã tồn tại: " + email);
    }
}
