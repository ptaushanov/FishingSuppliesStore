package com.ptaushanov.shop.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ptaushanov.shop.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    @JsonIgnore
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Description is mandatory")
    private String description;

    private String image;

    @NotNull(message = "Price is mandatory")
    private double price;

    @NotNull(message = "Quantity is mandatory")
    private int quantity;

    @NotNull(message = "Category is mandatory")
    private Long categoryId;

    @JsonIgnore
    private Category category;
}
