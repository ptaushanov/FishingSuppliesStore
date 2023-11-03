package com.ptaushanov.shop.service;

import com.ptaushanov.shop.dto.product.ProductRequestDTO;
import com.ptaushanov.shop.dto.product.ProductResponseDTO;
import com.ptaushanov.shop.model.Category;
import com.ptaushanov.shop.model.Product;
import com.ptaushanov.shop.repository.CategoryRepository;
import com.ptaushanov.shop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public Page<ProductResponseDTO> getAllProducts(Pageable pageable) {
        Page<Product> categoryPage = productRepository.findAll(pageable);
        return categoryPage.map(category -> modelMapper.map(category, ProductResponseDTO.class));
    }

    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product with id " + id + " does not exist")
        );
        return modelMapper.map(product, ProductResponseDTO.class);
    }

    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        // Retrieve category from productRequestDTO
        Long categoryId = productRequestDTO.getCategoryId();
        Category category = categoryRepository.findById(
                categoryId).orElseThrow(() -> new IllegalArgumentException(
                "Category with id " + categoryId + " does not exist")
        );
        productRequestDTO.setCategory(category);

        // Map productRequestDTO to Product, save it and remap it to ProductResponseDTO
        Product product = modelMapper.map(productRequestDTO, Product.class);
        return modelMapper.map(
                productRepository.save(product),
                ProductResponseDTO.class
        );
    }

    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        // Retrieve product and category from database
        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product with id " + id + " does not exist")
        );

        Long categoryId = productRequestDTO.getCategoryId();
        Category category = categoryRepository.findById(
                categoryId).orElseThrow(() -> new IllegalArgumentException(
                "Category with id " + categoryId + " does not exist")
        );

        // Update product properties
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setImage(productRequestDTO.getImage());
        product.setPrice(productRequestDTO.getPrice());
        product.setQuantity(productRequestDTO.getQuantity());
        product.setCategory(category);

        return modelMapper.map(product, ProductResponseDTO.class);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product with id " + id + " does not exist");
        }
        productRepository.deleteById(id);
    }
}
