package com.ptaushanov.shop.config;

import com.ptaushanov.shop.dto.RegisterRequestDTO;
import com.ptaushanov.shop.model.User;
import com.ptaushanov.shop.model.UserRole;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // RegisterRequestDTO -> User
        modelMapper.createTypeMap(RegisterRequestDTO.class, User.class)
                .addMapping(src -> UserRole.USER, User::setRole);

        return modelMapper;
    }
}
