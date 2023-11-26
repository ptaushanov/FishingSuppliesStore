package com.ptaushanov.shop.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ptaushanov.shop.model.Product;
import com.ptaushanov.shop.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    @JsonIgnore
    private Long id;

    @NotNull(message = "Customer id cannot be null")
    private Long customerId;
    
    @NotNull(message = "Product ids cannot be null")
    private List<Long> productIds;

    @JsonIgnore
    private User customer;

    @JsonIgnore
    private List<Product> products;
}
