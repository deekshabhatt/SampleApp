package com.example.features.home.domain.recipeDetail.repository

import com.example.features.home.domain.recipeDetail.entities.Recipe
import com.example.core.utility.Result

interface RecipeDetailRepository {
    suspend fun getRecipeDetails(recipeId: String): Result<Recipe>
}