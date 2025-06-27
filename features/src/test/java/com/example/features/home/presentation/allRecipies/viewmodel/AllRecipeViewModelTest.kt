package com.example.features.home.presentation.allRecipies.viewmodel

import com.example.features.home.domain.AllRecipes.entities.AllRecipes
import com.example.core.utility.Result
import com.example.features.home.domain.AllRecipes.entities.Recipe
import com.example.features.home.domain.AllRecipes.usecase.AllRecipesUseCase
import com.example.features.home.presentation.allRecipies.AllRecipesIntent
import com.example.features.home.presentation.allRecipies.mapper.AllRecipesUiMapper
import com.example.features.home.presentation.allRecipies.uistate.AllRecipesUiState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class AllRecipeViewModelTest {

    private val getAllRecipeUseCase = mockk<AllRecipesUseCase>()

    private val allRecipesUiMapper = mockk<AllRecipesUiMapper>()

    private val testClass = AllRecipeViewModel(getAllRecipeUseCase, allRecipesUiMapper)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `GIVEN initialisation WHEN load page THEN return loading`() {
        val result = testClass.allRecipes.value
        assert(result is AllRecipesUiState.LOADING)
    }

    @Test
    fun `GIVEN happy flow WHEN load page THEN return success`() = runTest {
        val recipe = getSampleRecipeData()
        coEvery { getAllRecipeUseCase() } returns Result.Success(
            recipe
        )
        coEvery {
            allRecipesUiMapper(recipe)
        } returns getUiStateSuccessData()
        testClass.handleEvent(AllRecipesIntent.LoadPage)
        val result = testClass.allRecipes.value
        assert(result is AllRecipesUiState.DataLoadedUiState)
        verify { allRecipesUiMapper(eq(recipe)) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN happy flow WHEN load page THEN return error`() = runTest {
        val throwable = mockk<Throwable>()
        coEvery { getAllRecipeUseCase() } returns Result.Error(throwable)
        every { throwable.message } returns IO_EXCEPTION

        // Act
        testClass.handleEvent(AllRecipesIntent.LoadPage)
        advanceUntilIdle()
        val result = testClass.allRecipes.value

        // Assert
        assert(result is AllRecipesUiState.ErrorUiState)
        assertEquals(IO_EXCEPTION, (result as AllRecipesUiState.ErrorUiState).errorMessage)

        verify(exactly = 0) { allRecipesUiMapper(any()) }
    }

    private fun getSampleRecipeData() = AllRecipes(
        total = 1,
        recipes = listOf(
            Recipe(
                id = ID,
                name = NAME,
                image = IMAGE_URL,
                cuisine = CUISINE,
                prepTimeMinutes = PREP_TIME,
                cookTimeMinutes = COOKING_TIME,
                difficulty = DIFFICULTY,
                caloriesPerServing = CALORIES_PER_SERVING,
                ingredients = listOf(),
                instructions = listOf(),
                tags = listOf(),
                userId = 1,
                rating = 1.0,
                reviewCount = 1,
                mealType = listOf()
            )
        )
    )

    private fun getUiStateSuccessData() = AllRecipesUiState.DataLoadedUiState(
        totalRecipes = 1,
        recipesList = listOf(
            AllRecipesUiState.RecipeUiState(
                id = ID_UI_STATE,
                name = NAME,
                imageUrl = IMAGE_URL,
                cuisine = CUISINE,
                prepTime = PREP_TIME_UI_STATE,
                cookingTime = COOKING_TIME_UI_STATE,
                difficulty = DIFFICULTY,
                caloriesPerServing = CALORIES_PER_SERVING_UI_STATE
            )
        )
    )

    private companion object {
        private const val IO_EXCEPTION = "IO exception"
        private const val NAME = "Kadhai Paneer"
        private const val IMAGE_URL = "imageURL"
        private const val CUISINE = "Mexican"
        private const val ID = 1
        private const val PREP_TIME = 30
        private const val COOKING_TIME = 10
        private const val DIFFICULTY = "Easy"
        private const val CALORIES_PER_SERVING = 100
        private const val ID_UI_STATE = "1"
        private const val PREP_TIME_UI_STATE = "30"
        private const val COOKING_TIME_UI_STATE = "10"
        private const val CALORIES_PER_SERVING_UI_STATE = "100"
    }
}