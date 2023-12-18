package com.ptaushanov.shop.config;

import com.ptaushanov.shop.dto.auth.RegisterRequestDTO;
import com.ptaushanov.shop.dto.category.CategoryRequestDTO;
import com.ptaushanov.shop.dto.order.OrderItemDTO;
import com.ptaushanov.shop.dto.order.OrderRequestDTO;
import com.ptaushanov.shop.dto.order.OrderResponseDTO;
import com.ptaushanov.shop.dto.product.ProductRequestDTO;
import com.ptaushanov.shop.model.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

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


        // OrderItemDTO -> OrderItem
        modelMapper.addConverter((Converter<List<OrderItemDTO>, List<OrderItem>>) context -> {
            List<OrderItemDTO> source = context.getSource();
            if (source == null) {
                return null;
            }
            return source.stream()
                    .map(dto -> modelMapper.map(dto, OrderItem.class))
                    .collect(Collectors.toList());
        });

        // OrderRequestDTO -> Order
        modelMapper.createTypeMap(OrderRequestDTO.class, Order.class)
                .addMapping(OrderRequestDTO::getId, Order::setId)
                .addMapping(src -> {
                    List<OrderItemDTO> orderItems = src.getOrderItems();
                    if (orderItems == null) {
                        return null;
                    }
                    return orderItems.stream()
                            .map(dto -> modelMapper.map(dto, OrderItem.class))
                            .collect(Collectors.toList());
                }, Order::setOrderItems)
                .addMapping(OrderRequestDTO::getCustomer, Order::setCustomer);

        // Order -> OrderResponseDTO
        modelMapper.createTypeMap(Order.class, OrderResponseDTO.class)
                .addMapping(
                        src -> src.getCustomer().getUsername(),
                        OrderResponseDTO::setCustomerUsername
                )
                .addMapping(src -> src.getCustomer().getEmail(), OrderResponseDTO::setCustomerEmail)
                .addMapping(src -> {
                    List<OrderItem> orderItems = src.getOrderItems();
                    if (orderItems == null) {
                        return null;
                    }
                    return orderItems.stream()
                            .map(orderItem -> modelMapper.map(orderItem, OrderItemDTO.class))
                            .collect(Collectors.toList());
                }, OrderResponseDTO::setOrderItems);

        return modelMapper;
    }
}
