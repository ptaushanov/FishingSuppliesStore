package com.ptaushanov.shop.config;

import com.ptaushanov.shop.mapper.RegistrationMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public RegistrationMapper registrationMapper() {
        return Mappers.getMapper(RegistrationMapper.class);
    }
}
