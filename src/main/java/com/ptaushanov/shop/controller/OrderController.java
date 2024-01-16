package com.ptaushanov.shop.controller;

import com.ptaushanov.shop.dto.order.OrderRequestDTO;
import com.ptaushanov.shop.dto.order.OrderResponseDTO;
import com.ptaushanov.shop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @Secured("ROLE_ADMIN")
    @GetMapping
    public Page<OrderResponseDTO> getAllOrders(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sort", defaultValue = "id,asc") String sortString
    ) {
        return orderService.getAllOrders(page, size, sortString);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/{id}")
    public OrderResponseDTO getCategoryById(@PathVariable(name = "id") Long id) {
        return orderService.getOrderById(id);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        return orderService.createOrder(orderRequestDTO);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(path = "/{id}")
    public void deleteOrder(@PathVariable(name = "id") Long id) {
        orderService.deleteOrder(id);
    }
}
