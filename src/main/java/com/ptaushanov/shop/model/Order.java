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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    private User customer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems;

    @Column(nullable = false)
    private double totalPrice;

    public Order(User customer, List<OrderItem> orderItems) {
        this.customer = customer;
        this.orderItems = orderItems;
    }

    @PrePersist
    @PreUpdate
    private void calculateTotalPrice() {
        if (orderItems != null) {
            double total = 0.0;
            for (OrderItem orderItem : orderItems) {
                total += orderItem.getProductPrice() * orderItem.getAmount();
            }
            this.totalPrice = total;
        }
    }
}
