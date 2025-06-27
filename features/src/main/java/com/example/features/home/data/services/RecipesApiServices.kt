package com.example.features.home.data.services

import com.example.features.home.data.allRecipe.dto.AllRecipesDto
import com.example.features.home.data.recipeDetail.dto.RecipesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipesApiServices {
    @GET("recipes")
    suspend fun getAllRecipes(): Response<AllRecipesDto>

    @GET("recipes/{recipeId}")
    suspend fun getRecipeDetail(@Path("recipeId") recipeId: String): Response<RecipesDto>
}