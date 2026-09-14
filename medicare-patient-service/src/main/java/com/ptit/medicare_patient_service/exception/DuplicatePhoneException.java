package com.ptit.medicare_patient_service.exception;

public class DuplicatePhoneException extends DuplicateResourceException {
    public DuplicatePhoneException(String phone) {
        super("Số điện thoại '" + phone + "' đã tồn tại trong hệ thống");
    }
}
