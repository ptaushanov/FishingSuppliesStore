package com.ptaushanov.shop.dto.user;

import com.ptaushanov.shop.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private UserRole role;
}
