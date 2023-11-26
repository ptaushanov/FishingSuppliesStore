package com.ptaushanov.shop.config;

import com.ptaushanov.shop.dto.auth.RegisterRequestDTO;
import com.ptaushanov.shop.dto.category.CategoryRequestDTO;
import com.ptaushanov.shop.dto.order.OrderRequestDTO;
import com.ptaushanov.shop.dto.product.ProductRequestDTO;
import com.ptaushanov.shop.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // UserRequestDTO -> User
        modelMapper.createTypeMap(RegisterRequestDTO.class, User.class)
                .addMapping(src -> UserRole.USER, User::setRole);

        // CategoryRequestDTO -> Category
        modelMapper.createTypeMap(CategoryRequestDTO.class, Category.class)
                .addMapping(CategoryRequestDTO::getId, Category::setId);

        // ProductRequestDTO -> Product
        modelMapper.createTypeMap(ProductRequestDTO.class, Product.class)
                .addMapping(ProductRequestDTO::getId, Product::setId);

        // OrderRequestDTO -> Order
        modelMapper.createTypeMap(OrderRequestDTO.class, Order.class)
                .addMapping(OrderRequestDTO::getId, Order::setId);

        return modelMapper;
    }
}
