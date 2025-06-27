package com.example.features.home.presentation.allRecipies.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.features.home.presentation.allRecipies.AllRecipeScreen
import com.example.features.home.presentation.recipieDetail.ui.RecipeDetailScreen

@Composable
fun AppNavigationHost(
    innerPadding: PaddingValues,
    navController: NavHostController
) {
    NavHost(navController, startDestination = NavigationItem.AllRecipes.route) {
        composable(NavigationItem.AllRecipes.route) {
            AllRecipeScreen(
                 innerPadding,
                navController
            )
        }

        composable(NavigationItem.RecipeDetail.route) {
            RecipeDetailScreen(innerPadding)
        }
    }
}