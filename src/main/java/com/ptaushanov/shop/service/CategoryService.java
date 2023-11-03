package com.ptaushanov.shop.service;

import com.ptaushanov.shop.dto.category.CategoryRequestDTO;
import com.ptaushanov.shop.dto.category.CategoryResponseDTO;
import com.ptaushanov.shop.model.Category;
import com.ptaushanov.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public Page<CategoryResponseDTO> getAllCategories(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return categoryPage.map(category -> modelMapper.map(category, CategoryResponseDTO.class));
    }

    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Category with id " + id + " does not exist")
        );
        return modelMapper.map(category, CategoryResponseDTO.class);
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        if (categoryRequestDTO.getParentCategoryId() == null) {
            Category category = modelMapper.map(categoryRequestDTO, Category.class);
            return modelMapper.map(
                    categoryRepository.save(category),
                    CategoryResponseDTO.class
            );
        }

        Long parentCategoryId = categoryRequestDTO.getParentCategoryId();
        Category parentCategory = categoryRepository.findById(
                parentCategoryId).orElseThrow(() -> new IllegalArgumentException(
                "Category with id " + parentCategoryId + " does not exist")
        );

        categoryRequestDTO.setParentCategory(parentCategory);
        Category category = modelMapper.map(categoryRequestDTO, Category.class);
        return modelMapper.map(
                categoryRepository.save(category),
                CategoryResponseDTO.class
        );
    }
}
