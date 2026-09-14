package com.ptit.medicare_pharmacy_service.exception;

public class DuplicateMedicineCodeException extends DuplicateResourceException {
    public DuplicateMedicineCodeException(String medicineCode) {
        super("Mã thuốc '" + medicineCode + "' đã tồn tại trong danh mục dược phẩm");
    }
}
