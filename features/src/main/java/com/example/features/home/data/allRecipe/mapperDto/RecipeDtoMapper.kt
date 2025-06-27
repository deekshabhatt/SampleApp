package com.example.features.home.data.allRecipe.mapperDto

import com.example.features.home.domain.AllRecipes.entities.Recipe
import jakarta.inject.Inject

class RecipeDtoMapper @Inject constructor() {

    operator fun invoke(recipeDto: com.example.features.home.data.allRecipe.dto.RecipesDto): Recipe {
        return Recipe(
            id = recipeDto.id,
            name = recipeDto.name,
            ingredients = recipeDto.ingredients,
            instructions = recipeDto.instructions,
            prepTimeMinutes = recipeDto.prepTimeMinutes,
            cookTimeMinutes = recipeDto.cookTimeMinutes,
            servings = recipeDto.servings,
            difficulty = recipeDto.difficulty,
            cuisine = recipeDto.cuisine,
            caloriesPerServing = recipeDto.caloriesPerServing,
            tags = recipeDto.tags,
            userId = recipeDto.userId,
            image = recipeDto.image,
            rating = recipeDto.rating,
            reviewCount = recipeDto.reviewCount,
            mealType = recipeDto.mealType
        )
    }
}