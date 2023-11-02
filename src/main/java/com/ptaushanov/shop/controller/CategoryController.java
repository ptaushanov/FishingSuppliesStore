package com.ptaushanov.shop.controller;

import com.ptaushanov.shop.dto.CategoryResponseDTO;
import com.ptaushanov.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/category")
@RequiredArgsConstructor
@Secured({"ROLE_ADMIN"})
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public CategoryResponseDTO getAllCategories(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
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
}
