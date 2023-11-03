package com.ptaushanov.shop.controller;

import com.ptaushanov.shop.dto.category.CategoryRequestDTO;
import com.ptaushanov.shop.dto.category.CategoryResponseDTO;
import com.ptaushanov.shop.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public Page<CategoryResponseDTO> getAllCategories(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sort", defaultValue = "id,asc") String sortString
    ) {
        return categoryService.getAllCategories(page, size, sortString);
    }

    @GetMapping(path = "/{id}")
    public CategoryResponseDTO getCategoryById(@PathVariable(name = "id") Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public CategoryResponseDTO createCategory(
            @RequestBody @Valid CategoryRequestDTO categoryRequestDTO) {
        return categoryService.createCategory(categoryRequestDTO);
    }

    @PutMapping(path = "/{id}")
    @Secured("ROLE_ADMIN")
    public CategoryResponseDTO updateCategory(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid CategoryRequestDTO categoryRequestDTO) {
        return categoryService.updateCategory(id, categoryRequestDTO);
    }

    @DeleteMapping(path = "/{id}")
    @Secured("ROLE_ADMIN")
    public void deleteCategory(@PathVariable(name = "id") Long id) {
        categoryService.deleteCategory(id);
    }

}
