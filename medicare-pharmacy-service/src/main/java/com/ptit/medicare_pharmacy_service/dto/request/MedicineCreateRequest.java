package com.ptit.medicare_pharmacy_service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ptit.medicare_pharmacy_service.validator.UniqueMedicineCode;
import com.ptit.medicare_pharmacy_service.validator.ValidMedicineStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineCreateRequest {

    @Size(max = 50, message = "Mã thuốc không được dài quá 50 ký tự")
    @UniqueMedicineCode
    private String medicineCode;

    @Size(max = 50, message = "Số đăng ký lưu hành không được dài quá 50 ký tự")
    private String registrationNumber;

    @NotBlank(message = "Tên thuốc không được để trống")
    @Size(max = 150, message = "Tên thuốc không được dài quá 150 ký tự")
    private String name;

    @Size(max = 150, message = "Tên hoạt chất không được dài quá 150 ký tự")
    private String activeIngredient;

    @Size(max = 100, message = "Nhóm dược lý không được dài quá 100 ký tự")
    private String category;

    @Size(max = 50, message = "Dạng bào chế không được dài quá 50 ký tự")
    private String dosageForm;

    @NotBlank(message = "Đơn vị tính không được để trống")
    @Size(max = 30, message = "Đơn vị tính không được dài quá 30 ký tự")
    private String unit;

    @NotNull(message = "Đơn giá bán không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Đơn giá bán phải lớn hơn hoặc bằng 0")
    private BigDecimal unitPrice;

    @NotNull(message = "Số lượng tồn kho ban đầu không được để trống")
    @Min(value = 0, message = "Số lượng tồn kho phải lớn hơn hoặc bằng 0")
    private Integer stockQuantity;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate expiryDate;

    @ValidMedicineStatus(message = "Trạng thái thuốc chỉ có thể là AVAILABLE, OUT_OF_STOCK, DISCONTINUED")
    private String status;
}
