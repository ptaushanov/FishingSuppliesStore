package com.ptaushanov.shop.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenericErrorResponseDTO {
    private String message;
    private String error;
}
