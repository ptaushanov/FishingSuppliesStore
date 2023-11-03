package com.ptaushanov.shop.config;

import com.ptaushanov.shop.dto.auth.RegisterRequestDTO;
import com.ptaushanov.shop.dto.category.CategoryRequestDTO;
import com.ptaushanov.shop.dto.product.ProductRequestDTO;
import com.ptaushanov.shop.model.Category;
import com.ptaushanov.shop.model.Product;
import com.ptaushanov.shop.model.User;
import com.ptaushanov.shop.model.UserRole;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // RegisterRequestDTO -> User
        modelMapper.createTypeMap(RegisterRequestDTO.class, User.class)
                .addMapping(src -> UserRole.USER, User::setRole);

        // CategoryRequestDTO -> Category
        modelMapper.createTypeMap(CategoryRequestDTO.class, Category.class)
                .addMapping(src -> src.getId(), Category::setId);

        // ProductRequestDTO -> Product
        modelMapper.createTypeMap(ProductRequestDTO.class, Product.class)
                .addMapping(src -> src.getId(), Product::setId);

        return modelMapper;
    }
}
