package com.ptaushanov.shop.service;

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


    public CategoryResponseDTO getAllCategories(Pageable pageable) {
        Page<Category> categoriesPage = categoryRepository.findAll(pageable);
        return modelMapper.map(categoriesPage, CategoryResponseDTO.class);
    }
}
