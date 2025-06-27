package com.example.features.home.domain.AllRecipes.usecase

import com.example.core.utility.Result
import com.example.features.home.domain.AllRecipes.entities.AllRecipes
import com.example.features.home.domain.AllRecipes.repository.AllRecipesRepository
import jakarta.inject.Inject

class AllRecipesUseCase @Inject constructor(
    private val allRecipesRepository: AllRecipesRepository
) {
    suspend operator fun invoke(): Result<AllRecipes> =
        allRecipesRepository.getAllRecipes()
}