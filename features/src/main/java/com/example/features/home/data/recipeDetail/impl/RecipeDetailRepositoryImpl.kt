package com.example.features.home.data.recipeDetail.impl


import com.example.core.network.contract.ApiExecutor
import com.example.core.utility.Result
import com.example.features.home.data.recipeDetail.RecipeDtoMapper
import com.example.features.home.data.services.RecipesApiServices
import com.example.features.home.domain.recipeDetail.entities.Recipe
import com.example.features.home.domain.recipeDetail.repository.RecipeDetailRepository
import jakarta.inject.Inject

internal class RecipeDetailRepositoryImpl @Inject constructor(
    private val recipesApiServices: RecipesApiServices,
    private val recipeDtoMapper: RecipeDtoMapper,
    private val apiExecutor: ApiExecutor
) : RecipeDetailRepository {
    override suspend fun getRecipeDetails(recipeId: String): Result<Recipe> {

        val result = apiExecutor{recipesApiServices.getRecipeDetail(recipeId)}
        return when(result){
            is Result.Success -> Result.Success(recipeDtoMapper(result.data))
            is Result.Error -> Result.Error(result.throwable)
        }
    }
}
