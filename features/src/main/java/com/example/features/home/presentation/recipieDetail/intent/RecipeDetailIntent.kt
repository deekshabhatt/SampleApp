package com.example.features.home.presentation.recipieDetail.intent

sealed interface RecipeDetailIntent {
    data object LoadPage : RecipeDetailIntent
}