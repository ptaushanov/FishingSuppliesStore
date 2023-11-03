package com.ptaushanov.shop.controller;

import com.ptaushanov.shop.dto.product.ProductRequestDTO;
import com.ptaushanov.shop.dto.product.ProductResponseDTO;
import com.ptaushanov.shop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public Page<ProductResponseDTO> getAllProducts(
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
        return productService.getAllProducts(pageable);
    }

    @GetMapping(path = "/{id}")
    public ProductResponseDTO getProductById(@PathVariable(name = "id") Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ProductResponseDTO createProduct(
            @RequestBody @Valid ProductRequestDTO productRequestDTO) {
        return productService.createProduct(productRequestDTO);
    }

    @PutMapping(path = "/{id}")
    @Secured("ROLE_ADMIN")
    public ProductResponseDTO updateProduct(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid ProductRequestDTO productRequestDTO) {
        return productService.updateProduct(id, productRequestDTO);
    }

    @DeleteMapping(path = "/{id}")
    @Secured("ROLE_ADMIN")
    public void deleteProduct(@PathVariable(name = "id") Long id) {
        productService.deleteProduct(id);
    }

}
