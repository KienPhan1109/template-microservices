package com.ptit.medicare_pharmacy_service.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineStockUpdateRequest {

    @NotNull(message = "Số lượng điều chỉnh không được để trống")
    private Integer quantity;

    @Pattern(regexp = "^(IMPORT|EXPORT|SET)$", message = "Loại điều chỉnh kho chỉ có thể là IMPORT (nhập thêm), EXPORT (xuất kho), SET (cân kho)")
    @Builder.Default
    private String type = "IMPORT";

    private String reason;
}
