package com.ptaushanov.shop.controller;

import com.ptaushanov.shop.dto.category.CategoryRequestDTO;
import com.ptaushanov.shop.dto.category.CategoryResponseDTO;
import com.ptaushanov.shop.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            @RequestParam(name = "sort", defaultValue = "id,asc") String sort
    ) {
        String sortArray[] = sort.split(",");

        if (sortArray.length < 2) {
            throw new IllegalArgumentException(
                    "Sort parameter must be in format: property,asc|desc"
            );
        }

        String property = sortArray[0];
        String direction = sortArray[1];

        Pageable pageable = PageRequest.of(
                page, size,
                Sort.by(Sort.Direction.fromString(direction), property)
        );
        return categoryService.getAllCategories(pageable);
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
