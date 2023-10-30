package com.ptaushanov.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}
