package com.ptit.medicare_pharmacy_service.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String medicineName, int requestedQuantity, int availableStock) {
        super(String.format("Không đủ số lượng tồn kho cho thuốc '%s'. Yêu cầu: %d, Hiện có trong kho: %d",
                medicineName, requestedQuantity, availableStock));
    }

    public InsufficientStockException(String message) {
        super(message);
    }
}
