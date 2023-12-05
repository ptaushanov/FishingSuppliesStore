package com.ptaushanov.shop.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ptaushanov.shop.model.Product;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    @JsonIgnore
    private Long id;

    @NotNull(message = "Product id cannot be null")
    private Long productId;

    @NotNull(message = "Product name cannot be null")
    private int amount;

    @JsonIgnore
    private Product product;
}
