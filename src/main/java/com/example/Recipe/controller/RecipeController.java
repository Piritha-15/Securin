package com.example.Recipe.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Recipe.entity.Recipe;
import com.example.Recipe.service.RecipeService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService service;

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe saved = service.createRecipe(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @GetMapping("/topFive")
    public ResponseEntity<Map<String, Object>> getTopRecipes(@RequestParam(defaultValue = "5") int limit) {
    List<Recipe> recipes = service.getTopRecipes(limit);
    Map<String, Object> response = new HashMap<>();
    response.put("data", recipes);
    return ResponseEntity.ok(response);
}
}