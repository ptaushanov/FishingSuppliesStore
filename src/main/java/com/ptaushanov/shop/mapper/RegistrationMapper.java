package com.ptaushanov.shop.mapper;

import com.ptaushanov.shop.dto.RegisterRequestDTO;
import com.ptaushanov.shop.model.User;
import com.ptaushanov.shop.model.UserRole;
import org.mapstruct.Mapper;

@Mapper
public interface RegistrationMapper {
    User mapToUser(RegisterRequestDTO registerRequestDTO);

    // Custom mapping to set the default role to USER
    default UserRole mapUserRole() {
        return UserRole.USER;
    }
}
