package com.example.features.home.domain.AllRecipes.repository

import com.example.features.home.domain.AllRecipes.entities.AllRecipes
import com.example.core.utility.Result


interface AllRecipesRepository {
    suspend fun getAllRecipes(): Result<AllRecipes>
}