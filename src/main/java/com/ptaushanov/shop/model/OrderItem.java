package com.ptaushanov.shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    private String productDescription;

    private String productImage;

    @Column(nullable = false)
    private double productPrice;

    @Column(nullable = false)
    private int amount;

    public OrderItem(Long productId, String name, String description, String image, double price, int amount) {
        this.productId = productId;
        this.productName = name;
        this.productDescription = description;
        this.productImage = image;
        this.productPrice = price;
        this.amount = amount;
    }
}