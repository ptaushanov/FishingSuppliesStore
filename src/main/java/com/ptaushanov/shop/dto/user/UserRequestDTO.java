package com.ptaushanov.shop.dto.user;

import com.ptaushanov.shop.model.UserRole;
import com.ptaushanov.shop.util.Password;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters long")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters long")
    private String lastName;
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 2, max = 30, message = "Username must be between 2 and 30 characters long")
    @Pattern(regexp = "^[a-zA-Z0-9_@]+$",
            message = "Username must contain only letters and digits and the following special characters: _ @")
    private String username;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    @Password
    private String password;

    @NotNull(message = "Role cannot be null")
    private UserRole role;
}
