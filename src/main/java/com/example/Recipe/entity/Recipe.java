package com.example.Recipe.entity;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "recipes")
@Getter
@Setter
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = true)
    private String title;
    @Column(nullable = true)
    private String cuisine;
    @Column(nullable = true)
    private Float rating;
    @JsonProperty("prep_time")
    private Integer prepTime;
    @JsonProperty("cook_time")
    private Integer cookTime;
    @JsonProperty("total_time")
    private Integer totalTime;
    @Column(columnDefinition = "TEXT", nullable = true)
    private String description;
    @ElementCollection
    @CollectionTable(name = "recipe_nutrients", joinColumns = @JoinColumn(name = "recipe_id"))
    @MapKeyColumn(name = "nutrient_key")
    @Column(name = "nutrient_value")
    private Map<String, String> nutrients;
    @Column(nullable = true)
    private String serves;
}