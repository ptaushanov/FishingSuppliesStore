package com.ptaushanov.shop.dto;

import com.ptaushanov.shop.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllCategoriesResponseDTO {
    private List<Category> content;
    private int totalPages;
    private long totalElements;
    private boolean last;

}
