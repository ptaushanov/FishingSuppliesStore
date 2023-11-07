package com.ptaushanov.shop.config;

import com.ptaushanov.shop.model.Category;
import com.ptaushanov.shop.model.Product;
import com.ptaushanov.shop.model.User;
import com.ptaushanov.shop.model.UserRole;
import com.ptaushanov.shop.repository.CategoryRepository;
import com.ptaushanov.shop.repository.ProductRepository;
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
    private final ProductRepository productRepository;
    private final Environment environment;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            String ddlAuto = environment.getProperty("spring.jpa.hibernate.ddl-auto");
            if (!ddlAuto.contains("create")) return;
            seedUsers();
            seedCategoriesAndProducts();
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

    private void seedCategoriesAndProducts() {
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

        // Example products for Spinning Rods
        Product spinningRod1 = Product.builder()
                .name("High-Performance Spinning Rod")
                .description("A top-notch spinning rod for finesse fishing.")
                .image("spinning_rod1.jpg")
                .price(129.99)
                .quantity(10)
                .category(spinningRods)
                .build();

        Product spinningRod2 = Product.builder()
                .name("Ultra-Light Spinning Rod")
                .description("An ultra-light spinning rod for delicate fishing techniques.")
                .image("spinning_rod2.jpg")
                .price(89.99)
                .quantity(12)
                .category(spinningRods)
                .build();

        // Example products for Casting Rods
        Product castingRod1 = Product.builder()
                .name("Heavy-Duty Casting Rod")
                .description("A powerful casting rod for long-distance casting.")
                .image("casting_rod1.jpg")
                .price(149.99)
                .quantity(8)
                .category(castingRods)
                .build();

        Product castingRod2 = Product.builder()
                .name("Premium Casting Rod")
                .description("A premium casting rod for professional anglers.")
                .image("casting_rod2.jpg")
                .price(169.99)
                .quantity(6)
                .category(castingRods)
                .build();

        // Example products for Baitcasting Reels
        Product baitcastingReel1 = Product.builder()
                .name("Baitcasting Reel with Magnetic Brake")
                .description("A baitcasting reel with adjustable magnetic brake.")
                .image("baitcasting_reel1.jpg")
                .price(119.99)
                .quantity(10)
                .category(baitcastingReels)
                .build();

        Product baitcastingReel2 = Product.builder()
                .name("High-Speed Baitcasting Reel")
                .description("A high-speed baitcasting reel for rapid retrieval.")
                .image("baitcasting_reel2.jpg")
                .price(139.99)
                .quantity(7)
                .category(baitcastingReels)
                .build();

        // Example products for Spinning Reels
        Product spinningReel1 = Product.builder()
                .name("Smooth Spinning Reel")
                .description("A smooth and versatile spinning reel for all types of fishing.")
                .image("spinning_reel1.jpg")
                .price(99.99)
                .quantity(15)
                .category(spinningReels)
                .build();

        Product spinningReel2 = Product.builder()
                .name("Lightweight Spinning Reel")
                .description("A lightweight spinning reel for finesse fishing.")
                .image("spinning_reel2.jpg")
                .price(79.99)
                .quantity(18)
                .category(spinningReels)
                .build();

        // Example products for Soft Baits
        Product softBait1 = Product.builder()
                .name("Realistic Soft Worm Bait")
                .description("A realistic soft bait to mimic natural prey.")
                .image("soft_bait1.jpg")
                .price(5.99)
                .quantity(50)
                .category(softBaits)
                .build();

        Product softBait2 = Product.builder()
                .name("Soft Shrimp Lure")
                .description("A soft shrimp lure to attract various fish species.")
                .image("soft_bait2.jpg")
                .price(7.99)
                .quantity(40)
                .category(softBaits)
                .build();

        // Example products for Hard Baits
        Product hardBait1 = Product.builder()
                .name("Diving Crankbait")
                .description("A diving crankbait with lifelike actions.")
                .image("hard_bait1.jpg")
                .price(8.99)
                .quantity(30)
                .category(hardBaits)
                .build();

        Product hardBait2 = Product.builder()
                .name("Topwater Popper Lure")
                .description("A topwater popper lure that creates a commotion on the surface.")
                .image("hard_bait2.jpg")
                .price(9.99)
                .quantity(25)
                .category(hardBaits)
                .build();

        // Save the products
        productRepository.saveAll(List.of(
                spinningRod1, spinningRod2, castingRod1, castingRod2, baitcastingReel1,
                baitcastingReel2,
                spinningReel1, spinningReel2, softBait1, softBait2, hardBait1, hardBait2
        ));
    }
}
