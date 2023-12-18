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

import java.util.ArrayList;
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
        // Add a custom mapping for the order items
        Converter<List<OrderItemDTO>, ArrayList<OrderItem>> orderItemsConverter = context ->
                context.getSource().stream()
                        .map(orderItem -> OrderItem
                                .builder()
                                .id(orderItem.getId())
                                .productId(orderItem.getProductId())
                                .productName(orderItem.getProduct().getName())
                                .productDescription(orderItem.getProduct().getDescription())
                                .productImage(orderItem.getProduct().getImage())
                                .productPrice(orderItem.getProduct().getPrice())
                                .amount(orderItem.getAmount())
                                .build()
                        )
                        .collect(Collectors.toCollection(ArrayList::new));

        // OrderRequestDTO -> Order
        modelMapper.createTypeMap(OrderRequestDTO.class, Order.class)
                .addMapping(OrderRequestDTO::getId, Order::setId)
                .addMappings(mapper -> mapper.using(orderItemsConverter)
                        .map(OrderRequestDTO::getOrderItems, Order::setOrderItems))
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
