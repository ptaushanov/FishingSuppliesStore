package com.ptaushanov.shop.dto.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ptaushanov.shop.model.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDTO {
    @JsonIgnore
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Description is mandatory")
    private String description;

    private Long parentCategoryId;

    @JsonIgnore
    private Category parentCategory;
}
