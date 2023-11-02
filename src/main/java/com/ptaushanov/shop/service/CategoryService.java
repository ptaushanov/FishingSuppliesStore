package com.ptaushanov.shop.service;

import com.ptaushanov.shop.dto.AllCategoriesResponseDTO;
import com.ptaushanov.shop.dto.CreateCategoryDTO;
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

    public AllCategoriesResponseDTO getAllCategories(Pageable pageable) {
        Page<Category> categoriesPage = categoryRepository.findAll(pageable);
        return modelMapper.map(categoriesPage, AllCategoriesResponseDTO.class);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Category with id " + id + " does not exist")
        );
    }

    public Category createCategory(CreateCategoryDTO createCategoryDTO) {
        if (createCategoryDTO.getParentCategoryId() == null) {
            Category category = modelMapper.map(createCategoryDTO, Category.class);
            return categoryRepository.save(category);
        }

        Long parentCategoryId = createCategoryDTO.getParentCategoryId();
        Category parentCategory = categoryRepository.findById(
                parentCategoryId).orElseThrow(() -> new IllegalArgumentException(
                "Category with id " + parentCategoryId + " does not exist")
        );
        
        createCategoryDTO.setParentCategory(parentCategory);
        Category category = modelMapper.map(createCategoryDTO, Category.class);
        return categoryRepository.save(category);
    }
}
