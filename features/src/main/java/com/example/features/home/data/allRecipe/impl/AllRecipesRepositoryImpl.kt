package com.example.features.home.data.allRecipe.impl

import com.example.core.network.contract.ApiExecutor
import com.example.core.utility.Result
import com.example.features.home.domain.AllRecipes.entities.AllRecipes
import com.example.features.home.domain.AllRecipes.repository.AllRecipesRepository
import com.example.features.home.data.allRecipe.mapperDto.AllRecipesDtoMapper
import com.example.features.home.data.services.RecipesApiServices
import jakarta.inject.Inject

internal class AllRecipesRepositoryImpl @Inject constructor(
    private val recipesApiServices: RecipesApiServices,
    private val allRecipesDtoMapper: AllRecipesDtoMapper,
    private val apiExecutor: ApiExecutor
) : AllRecipesRepository {

    override suspend fun getAllRecipes(): Result<AllRecipes> {
        val result = apiExecutor { recipesApiServices.getAllRecipes() }
        return when (result) {
            is Result.Success -> Result.Success(allRecipesDtoMapper(result.data))
            is Result.Error -> Result.Error(result.throwable)
        }
    }
}
