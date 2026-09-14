package com.ptit.medicare_pharmacy_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionItemRequest {

    @NotNull(message = "Mã ID thuốc không được để trống")
    @Positive(message = "Mã ID thuốc phải là số nguyên dương")
    private Long medicineId;

    @NotNull(message = "Số lượng thuốc kê không được để trống")
    @Min(value = 1, message = "Số lượng thuốc kê tối thiểu phải từ 1 trở lên")
    private Integer quantity;

    @NotBlank(message = "Liều dùng không được để trống")
    @Size(max = 100, message = "Liều dùng không được dài quá 100 ký tự")
    private String dosage;

    @Size(max = 255, message = "Hướng dẫn sử dụng không được dài quá 255 ký tự")
    private String instruction;

    @DecimalMin(value = "0.0", inclusive = true, message = "Đơn giá xuất phải lớn hơn hoặc bằng 0")
    private BigDecimal unitPrice;
}
