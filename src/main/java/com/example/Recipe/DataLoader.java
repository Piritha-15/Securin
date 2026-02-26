package com.example.Recipe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.Recipe.service.RecipeService;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RecipeService service;
    @Override
    public void run(String... args) throws Exception {
        service.importData();
        System.out.println("DATA IMPORTED SUCCESSFULLY");
    }
}