package com.example.Recipe.repository;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Recipe.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findAllByOrderByRatingDesc(Pageable pageable);


}
