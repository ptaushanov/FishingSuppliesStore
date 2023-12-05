package com.ptaushanov.shop.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private Date createdAt;
    private String customerUsername;
    private String customerEmail;

    private List<OrderItemDTO> orderItems;
    private double totalPrice;
}
