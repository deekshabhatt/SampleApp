package com.example.features.home.presentation.allRecipies

sealed interface AllRecipesIntent {
    data object LoadPage : AllRecipesIntent
    data object RetryPageLoad : AllRecipesIntent
}
