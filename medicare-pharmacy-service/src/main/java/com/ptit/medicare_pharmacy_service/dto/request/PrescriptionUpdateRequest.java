package com.ptit.medicare_pharmacy_service.dto.request;

import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionUpdateRequest {

    private String notes;

    @Valid
    private List<PrescriptionItemRequest> items;
}
