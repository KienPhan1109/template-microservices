package com.ptit.medicare_doctor_service.exception;

public class DuplicatePhoneException extends DuplicateResourceException {
    public DuplicatePhoneException(String phone) {
        super("Số điện thoại đã tồn tại: " + phone);
    }
}
