package com.example.features.home.data.allRecipe.dto

import com.google.gson.annotations.SerializedName


data class AllRecipesDto(
    @SerializedName("recipes") val recipes: List<RecipesDto>,
    @SerializedName("total") val total: Int? = null
)