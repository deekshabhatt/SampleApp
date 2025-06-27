package com.example.features.home.presentation.allRecipies.mapper


import com.example.features.home.domain.AllRecipes.entities.AllRecipes
import com.example.features.home.presentation.allRecipies.uistate.AllRecipesUiState
import jakarta.inject.Inject

class AllRecipesUiMapper @Inject constructor() {

    operator fun invoke(allRecipes: AllRecipes): AllRecipesUiState {
        return AllRecipesUiState.DataLoadedUiState(
            totalRecipes = allRecipes.total ?: 0,
            recipesList = allRecipes.recipes.map {
               AllRecipesUiState.RecipeUiState(
                    name = it.name ?: "",
                    imageUrl = it.image ?: "",
                    prepTime = it.prepTimeMinutes?.toString() ?: "",
                    id = it.id.toString(),
                    cookingTime = it.cookTimeMinutes?.toString() ?: "",
                    difficulty = it.difficulty ?: "",
                    cuisine = it.cuisine ?: "",
                    caloriesPerServing = it.caloriesPerServing?.toString() ?: ""
                )
            }
        )
    }
}