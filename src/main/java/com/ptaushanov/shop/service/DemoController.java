package com.ptaushanov.shop.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/demo")
public class DemoController {
    @GetMapping
    public String sayHello() {
        return "Hello from secured API";
    }
}
