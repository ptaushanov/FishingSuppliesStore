package com.ptaushanov.shop.service;

import com.ptaushanov.shop.dto.CategoryRequestDTO;
import com.ptaushanov.shop.dto.CategoryResponseDTO;
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

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Category with id " + id + " does not exist")
        );
    }

    public Category createCategory(CategoryRequestDTO categoryRequestDTO) {
        if (categoryRequestDTO.getParentCategoryId() == null) {
            Category category = modelMapper.map(categoryRequestDTO, Category.class);
            return categoryRepository.save(category);
        }

        Long parentCategoryId = categoryRequestDTO.getParentCategoryId();
        Category parentCategory = categoryRepository.findById(
                parentCategoryId).orElseThrow(() -> new IllegalArgumentException(
                "Category with id " + parentCategoryId + " does not exist")
        );

        categoryRequestDTO.setParentCategory(parentCategory);
        Category category = modelMapper.map(categoryRequestDTO, Category.class);
        return categoryRepository.save(category);
    }
}
