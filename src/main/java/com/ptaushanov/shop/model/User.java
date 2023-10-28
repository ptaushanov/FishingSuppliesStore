package com.ptaushanov.shop.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role; // "CUSTOMER" or "OWNER"

    private LocalDate dob;

    public User(String username, String email, String password, UserRole role, LocalDate dob) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.dob = dob;
    }
}
