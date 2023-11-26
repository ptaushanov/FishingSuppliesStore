package com.ptaushanov.shop.service;

import com.ptaushanov.shop.dto.order.OrderRequestDTO;
import com.ptaushanov.shop.dto.order.OrderResponseDTO;
import com.ptaushanov.shop.model.Order;
import com.ptaushanov.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.ptaushanov.shop.util.PageableHelpers.createPageable;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;


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

    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        // If parentOrderId is null, create order without parentOrder
        if (orderRequestDTO.getParentOrderId() == null) {
            Order order = modelMapper.map(orderRequestDTO, Order.class);
            return modelMapper.map(
                    orderRepository.save(order),
                    OrderResponseDTO.class
            );
        }

        // If parentOrderId not null, set parentOrder
        Long parentOrderId = orderRequestDTO.getParentOrderId();
        Order parentOrder = orderRepository.findById(
                parentOrderId).orElseThrow(() -> new IllegalArgumentException(
                "Order with id " + parentOrderId + " does not exist")
        );
        orderRequestDTO.setParentOrder(parentOrder);

        // Map orderRequestDTO to Order, save it and remap it to OrderResponseDTO
        Order order = modelMapper.map(orderRequestDTO, Order.class);
        return modelMapper.map(
                orderRepository.save(order),
                OrderResponseDTO.class
        );
    }
}
