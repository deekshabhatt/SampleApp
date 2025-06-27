package com.example.features.home.data.allRecipe.mapperDto

import com.example.features.home.data.allRecipe.dto.AllRecipesDto
import com.example.features.home.domain.AllRecipes.entities.AllRecipes
import jakarta.inject.Inject

class AllRecipesDtoMapper @Inject constructor(
    private val recipeDtoMapper: RecipeDtoMapper
) {

    operator fun invoke(
        allRecipes: AllRecipesDto
    ): AllRecipes {
        return AllRecipes(
            recipes = allRecipes.recipes.map {
                return@map recipeDtoMapper(it)
            },
            total = allRecipes.total
        )
    }
}