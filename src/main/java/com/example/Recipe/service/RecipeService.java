package com.example.Recipe.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map; 

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.Recipe.entity.Recipe;
import com.example.Recipe.repository.RecipeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository repository;
    private final ObjectMapper mapper = new ObjectMapper();

    @Transactional
public void importData() throws Exception {

    if (repository.count() > 0) {
        return;   
    }
    File file = new File("D:/Downloads/US_recipes_null.json");

    if (!file.exists()) {
        throw new RuntimeException("JSON file not found at given path");
    }
    JsonNode root = mapper.readTree(file);
    List<Recipe> recipes = new ArrayList<>();
    if (root.isObject()) {
        root.fields().forEachRemaining(entry -> {
            recipes.add(parseRecipe(entry.getValue()));
        });
    } else if (root.isArray()) {
        for (JsonNode node : root) {
            recipes.add(parseRecipe(node));
        }
    }
    repository.saveAll(recipes);
}
    private Recipe parseRecipe(JsonNode node) {
        Recipe recipe = new Recipe();
        recipe.setCuisine(getText(node, "cuisine"));
        recipe.setTitle(getText(node, "title"));
        recipe.setRating(parseFloat(node.get("rating")));
        recipe.setPrepTime(parseInt(node.get("prep_time")));
        recipe.setCookTime(parseInt(node.get("cook_time")));
        recipe.setTotalTime(parseInt(node.get("total_time")));
        recipe.setDescription(getText(node, "description"));
        recipe.setServes(getText(node, "serves"));
        JsonNode nutrientsNode = node.get("nutrients");
        if (nutrientsNode != null && !nutrientsNode.isNull()) {
            Map<String, String> nutrientsMap =mapper.convertValue(nutrientsNode, new TypeReference<Map<String, String>>() {});
            recipe.setNutrients(nutrientsMap);
        }
        return recipe;
    }
    private String getText(JsonNode node, String field) {
        JsonNode value = node.get(field);
        return (value != null && !value.isNull())? value.asText():null;
    }
    private Float parseFloat(JsonNode node) {
        if (node == null || node.isNull()) return null;
        try {
            return node.floatValue();
        } catch (Exception e) {
            return null;
        }
    }
    private Integer parseInt(JsonNode node) {
        if (node == null || node.isNull()) return null;
        if ("NaN".equalsIgnoreCase(node.asText())) return null;
        try {
            return node.intValue();
        } catch (Exception e) {
            return null;
        }
    }
    public List<Recipe> getAllRecipes() {
        return repository.findAll();
    }
    public Recipe createRecipe(Recipe recipe) {

    if (recipe.getTitle() == null || recipe.getTitle().isBlank()) {
        throw new RuntimeException("title is required");
    }
    if (recipe.getCuisine() == null || recipe.getCuisine().isBlank()) {
        throw new RuntimeException("cuisine is required");
    }
    if (recipe.getPrepTime() == null) {
        throw new RuntimeException("prepTime is required");
    }
    if (recipe.getCookTime() == null) {
        throw new RuntimeException("cookTime is required");
    }
    recipe.setTotalTime(recipe.getPrepTime() + recipe.getCookTime());

    return repository.save(recipe);
}
    public List<Recipe> getTopRecipes(int limit) {

        Pageable pageable = PageRequest.of(0, limit);
        return repository.findAllByOrderByRatingDesc(pageable);
    }
}