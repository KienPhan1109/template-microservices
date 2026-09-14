package com.ptit.medicare_doctor_service.exception;

public class DuplicateDepartmentException extends DuplicateResourceException {
    public DuplicateDepartmentException(String message) {
        super(message);
    }
}
