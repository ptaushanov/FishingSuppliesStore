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
        // TODO: Figure out why this doesn't work for product id
//        modelMapper.createTypeMap(OrderItemDTO.class, OrderItem.class)
//                .addMapping(OrderItemDTO::getId, OrderItem::setId)
//                .addMapping(OrderItemDTO::getProductId, OrderItem::setProductId)
//                .addMapping(src -> src.getProduct().getName(), OrderItem::setProductName)
//                .addMapping(src -> src.getProduct().getDescription(), OrderItem::setProductDescription)
//                .addMapping(src -> src.getProduct().getImage(), OrderItem::setProductImage)
//                .addMapping(src -> src.getProduct().getPrice(), OrderItem::setProductPrice)
//                .addMapping(OrderItemDTO::getAmount, OrderItem::setAmount);

        // Add a custom mapping for the order item list
        Converter<List<OrderItemDTO>, List<OrderItem>> orderItemsConverter = context ->
                context.getSource().stream()
                        .map(orderItemDTO -> OrderItem // explicit mapping
                                .builder()
                                .id(orderItemDTO.getId())
                                .productId(orderItemDTO.getProductId())
                                .productName(orderItemDTO.getProduct().getName())
                                .productDescription(orderItemDTO.getProduct().getDescription())
                                .productImage(orderItemDTO.getProduct().getImage())
                                .productPrice(orderItemDTO.getProduct().getPrice())
                                .amount(orderItemDTO.getAmount())
                                .build()
                        )
                        .collect(Collectors.toList());

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
