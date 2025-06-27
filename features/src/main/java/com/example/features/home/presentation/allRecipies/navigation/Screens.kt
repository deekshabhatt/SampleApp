package com.example.features.home.presentation.allRecipies.navigation

sealed class NavigationItem(val route: String) {

    data object AllRecipes : NavigationItem("all_recipes")

    data object RecipeDetail : NavigationItem("recipe_detail/{recipeId}") {
        fun recipeId(recipeId: String) = "recipe_detail/$recipeId"
    }
}
