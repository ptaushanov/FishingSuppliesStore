package com.ptaushanov.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ptaushanov.shop.mapper") // Managing mappers as beans
public class FishingSuppliesShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(FishingSuppliesShopApplication.class, args);
    }

}
