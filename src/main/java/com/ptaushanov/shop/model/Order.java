package com.ptaushanov.shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User customer;

    @ManyToMany
    private List<Product> products;

    @Column(nullable = false)
    private double totalPrice;

    public Order(User customer, List<Product> products) {
        this.customer = customer;
        this.products = products;
    }

    @PrePersist
    @PreUpdate
    private void calculateTotalPrice() {
        if (products != null) {
            double total = 0.0;
            for (Product product : products) {
                total += product.getPrice();
            }
            this.totalPrice = total;
        }
    }
}
