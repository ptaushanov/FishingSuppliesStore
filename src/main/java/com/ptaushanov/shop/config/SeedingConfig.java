package com.ptaushanov.shop.config;

import com.ptaushanov.shop.model.User;
import com.ptaushanov.shop.model.UserRole;
import com.ptaushanov.shop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SeedingConfig {
    @Bean
    CommandLineRunner commandLineRunner(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            User admin = User.builder()
                    .firstName("Admin")
                    .lastName("Admin")
                    .username("admin")
                    .email("admin@internal.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(UserRole.ADMIN)
                    .build();

            User ivan = User.builder()
                    .firstName("Ivan")
                    .lastName("Petrov")
                    .username("ivan_petrov")
                    .email("ipetrov@gmail.com")
                    .password(passwordEncoder.encode("123456"))
                    .role(UserRole.USER)
                    .build();

            userRepository.save(admin);
        };

    }
}
