package com.ptaushanov.shop.config;

import com.ptaushanov.shop.model.Category;
import com.ptaushanov.shop.model.User;
import com.ptaushanov.shop.model.UserRole;
import com.ptaushanov.shop.repository.CategoryRepository;
import com.ptaushanov.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SeedingConfig {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final Environment environment;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            String ddlAuto = environment.getProperty("spring.jpa.hibernate.ddl-auto");
            if (!ddlAuto.contains("create")) return;
            seedUsers();
            seedCategories();
        };

    }

    private void seedUsers() {
        // Admin and user
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
                .username("ipetrov")
                .email("ipetrov@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .role(UserRole.USER)
                .build();

        userRepository.saveAll(List.of(admin, ivan));
    }

    private void seedCategories() {
        // Root-level categories
        Category fishingRods = Category.builder()
                .name("Fishing Rods")
                .description("Quality fishing rods for all types of fishing.")
                .parentCategory(null)
                .build();

        Category fishingReels = Category.builder()
                .name("Fishing Reels")
                .description("Durable fishing reels for smooth casting and retrieval.")
                .parentCategory(null)
                .build();

        Category fishingLures = Category.builder()
                .name("Fishing Lures")
                .description("A wide variety of lures to attract fish.")
                .parentCategory(null)
                .build();

        categoryRepository.saveAll(List.of(fishingRods, fishingReels, fishingLures));

        // Subcategories
        Category spinningRods = Category.builder()
                .name("Spinning Rods")
                .description("Lightweight spinning rods for finesse fishing.")
                .parentCategory(fishingRods)
                .build();

        Category castingRods = Category.builder()
                .name("Casting Rods")
                .description("Heavy-duty casting rods for long casts.")
                .parentCategory(fishingRods)
                .build();

        Category baitcastingReels = Category.builder()
                .name("Baitcasting Reels")
                .description("Baitcasting reels for precision casting.")
                .parentCategory(fishingReels)
                .build();

        Category spinningReels = Category.builder()
                .name("Spinning Reels")
                .description("Smooth and versatile spinning reels.")
                .parentCategory(fishingReels)
                .build();

        Category softBaits = Category.builder()
                .name("Soft Baits")
                .description("Realistic soft baits to mimic natural prey.")
                .parentCategory(fishingLures)
                .build();

        Category hardBaits = Category.builder()
                .name("Hard Baits")
                .description("Durable hard baits with lifelike actions.")
                .parentCategory(fishingLures)
                .build();

        categoryRepository.saveAll(List.of(
                spinningRods, castingRods, baitcastingReels, spinningReels, softBaits, hardBaits
        ));
    }
}
