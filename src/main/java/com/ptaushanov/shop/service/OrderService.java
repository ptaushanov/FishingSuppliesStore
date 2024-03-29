package com.ptaushanov.shop.service;

import com.ptaushanov.shop.dto.order.OrderItemDTO;
import com.ptaushanov.shop.dto.order.OrderRequestDTO;
import com.ptaushanov.shop.dto.order.OrderResponseDTO;
import com.ptaushanov.shop.model.Order;
import com.ptaushanov.shop.model.Product;
import com.ptaushanov.shop.model.User;
import com.ptaushanov.shop.repository.OrderRepository;
import com.ptaushanov.shop.repository.ProductRepository;
import com.ptaushanov.shop.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ptaushanov.shop.util.PageableHelpers.createPageable;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    public Page<OrderResponseDTO> getAllOrders(
            int page, int size, String sortString
    ) {
        Pageable pageable = createPageable(page, size, sortString);
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return orderPage.map(order -> modelMapper.map(order, OrderResponseDTO.class));
    }

    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Order with id " + id + " does not exist")
        );
        return modelMapper.map(order, OrderResponseDTO.class);
    }

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        // Check if user exists
        User customer = userRepository.findById(orderRequestDTO.getCustomerId()).orElseThrow(
                () -> new IllegalArgumentException(
                        "User with id " + orderRequestDTO.getCustomerId() + " does not exist")
        );
        orderRequestDTO.setCustomer(customer);

        // Get all product ids
        List<Long> productIds = orderRequestDTO.getOrderItems().stream()
                .map(OrderItemDTO::getProductId).toList();

        // Check if all products exist
        List<Product> products = productRepository.findAllById(productIds);
        if (products.size() != productIds.size()) throw new IllegalArgumentException(
                "One or more products do not exist");

        // Check quantity of products and update it
        for (Product product : products) {
            for (OrderItemDTO orderItemDTO : orderRequestDTO.getOrderItems()) {
                if (product.getId().equals(orderItemDTO.getProductId())) {
                    // Set the product to the orderItemDTO
                    orderItemDTO.setProduct(product);

                    // Check if product has enough quantity
                    if (product.getQuantity() < orderItemDTO.getAmount())
                        throw new IllegalArgumentException(
                                "Product with id " + product.getId() +
                                " does not have enough quantity");

                    // Update product quantity
                    product.setQuantity(product.getQuantity() - orderItemDTO.getAmount());
                }
            }
        }

        Order order = modelMapper.map(orderRequestDTO, Order.class);
        return modelMapper.map(orderRepository.save(order), OrderResponseDTO.class);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) throw new IllegalArgumentException(
                "Order with id " + id + " does not exist");
        orderRepository.deleteById(id);
    }
}
