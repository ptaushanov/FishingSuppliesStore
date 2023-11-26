package com.ptaushanov.shop.controller;

import com.ptaushanov.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
}
