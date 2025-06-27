package com.example.features.home.domain.recipeDetail.usecase

import com.example.features.home.domain.recipeDetail.entities.Recipe
import com.example.core.utility.Result
import com.example.features.home.domain.recipeDetail.repository.RecipeDetailRepository
import jakarta.inject.Inject

class RecipeDetailUseCase @Inject constructor(
    private val recipeDetailRepository: RecipeDetailRepository
) {
    suspend operator fun invoke(recipeId: String): Result<Recipe> {
        return recipeDetailRepository.getRecipeDetails(recipeId)
    }
}