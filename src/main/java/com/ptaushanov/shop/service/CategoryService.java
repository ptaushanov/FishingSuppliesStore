package com.ptaushanov.shop.service;

import com.ptaushanov.shop.dto.category.CategoryRequestDTO;
import com.ptaushanov.shop.dto.category.CategoryResponseDTO;
import com.ptaushanov.shop.model.Category;
import com.ptaushanov.shop.repository.CategoryRepository;
import jakarta.transaction.Transactional;
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
        // If parentCategoryId is null, create category without parentCategory
        if (categoryRequestDTO.getParentCategoryId() == null) {
            Category category = modelMapper.map(categoryRequestDTO, Category.class);
            return modelMapper.map(
                    categoryRepository.save(category),
                    CategoryResponseDTO.class
            );
        }

        // If parentCategoryId not null, set parentCategory
        Long parentCategoryId = categoryRequestDTO.getParentCategoryId();
        Category parentCategory = categoryRepository.findById(
                parentCategoryId).orElseThrow(() -> new IllegalArgumentException(
                "Category with id " + parentCategoryId + " does not exist")
        );
        categoryRequestDTO.setParentCategory(parentCategory);

        // Map categoryRequestDTO to Category, save it and remap it to CategoryResponseDTO
        Category category = modelMapper.map(categoryRequestDTO, Category.class);
        return modelMapper.map(
                categoryRepository.save(category),
                CategoryResponseDTO.class
        );
    }

    @Transactional
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Category with id " + id + " does not exist")
        );

        // If parentCategoryId not null, set parentCategory else set null
        Long parentCategoryId = categoryRequestDTO.getParentCategoryId();
        Category parentCategory = parentCategoryId != null ? categoryRepository.findById(
                parentCategoryId).orElseThrow(() -> new IllegalArgumentException(
                "Category with id " + parentCategoryId + " does not exist")
        ) : null;
        category.setParentCategory(parentCategory);

        // Set name and description from categoryRequestDTO
        category.setName(categoryRequestDTO.getName());
        category.setDescription(categoryRequestDTO.getDescription());

        return modelMapper.map(category, CategoryResponseDTO.class);
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category with id " + id + " does not exist");
        }
        categoryRepository.deleteById(id);
    }
}
