package com.example.features.home.data.impl.allRecipes.impl

import com.example.core.network.impl.ApiExecutorImpl
import com.example.core.utility.Result
import com.example.features.home.data.allRecipe.dto.AllRecipesDto
import com.example.features.home.data.allRecipe.dto.RecipesDto
import com.example.features.home.data.allRecipe.impl.AllRecipesRepositoryImpl
import com.example.features.home.data.allRecipe.mapperDto.AllRecipesDtoMapper
import com.example.features.home.data.impl.CALORIE_PER_SERVING
import com.example.features.home.data.impl.COOK_TIME
import com.example.features.home.data.impl.CUISINE
import com.example.features.home.data.impl.DIFFICULTY
import com.example.features.home.data.impl.ERROR_MSG
import com.example.features.home.data.impl.ERROR_MSG_BODY_NULL
import com.example.features.home.data.impl.ERROR_MSG_UNCAUGHT_EXCEPTION
import com.example.features.home.data.impl.ID
import com.example.features.home.data.impl.IMAGE
import com.example.features.home.data.impl.INGREDIENT_HONEY
import com.example.features.home.data.impl.INGREDIENT_PEPPER
import com.example.features.home.data.impl.INGREDIENT_SALT
import com.example.features.home.data.impl.INSTRUCTION_FIRST
import com.example.features.home.data.impl.INSTRUCTION_TWO
import com.example.features.home.data.impl.MEAL_TYPE
import com.example.features.home.data.impl.MEAL_TYPE_TWO
import com.example.features.home.data.impl.NAME
import com.example.features.home.data.impl.PREP_TIME
import com.example.features.home.data.impl.RATING
import com.example.features.home.data.impl.RECIPE_COUNT
import com.example.features.home.data.impl.REVIEW_COUNT
import com.example.features.home.data.impl.SERVING
import com.example.features.home.data.impl.TAG1
import com.example.features.home.data.impl.TAG2
import com.example.features.home.data.impl.USER_ID
import com.example.features.home.data.services.RecipesApiServices
import com.example.features.home.domain.AllRecipes.entities.AllRecipes
import com.example.features.home.domain.AllRecipes.entities.Recipe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response

class AllRecipesRepositoryImplTest {

    private val recipesApiServices = mockk<RecipesApiServices>()

    private val apiExecutor = ApiExecutorImpl()

    private val allRecipesDtoMapper = mockk<AllRecipesDtoMapper>()


    private val testClass = AllRecipesRepositoryImpl(
        recipesApiServices,
        allRecipesDtoMapper,
        apiExecutor
    )

    @Test
    fun `GIVEN happy path WHEN call getAllRecipes THEN return success`() = runTest {
        val captureDto = slot<AllRecipesDto>()
        val apiServiceResMock = mockk<Response<AllRecipesDto>>()
        coEvery { apiServiceResMock.isSuccessful } returns true
        coEvery { apiServiceResMock.body() } returns provideAllRecipeDtoSampleData()
        coEvery {
            recipesApiServices.getAllRecipes()
        } returns apiServiceResMock

        coEvery { allRecipesDtoMapper(capture(captureDto)) } returns provideAllRecipeSampleData()
        val result = testClass.getAllRecipes()
        assert(result is Result.Success)
        TestCase.assertEquals(provideAllRecipeSampleData(), (result as Result.Success).data)
        coVerify(exactly = 1) {
            allRecipesDtoMapper(any())
        }
        TestCase.assertEquals(captureDto.captured, provideAllRecipeDtoSampleData())
    }

    @Test
    fun `GIVEN happy path WHEN call getAllRecipes THEN return empty body response`() = runTest {
        val expected = Result.Error(Throwable(ERROR_MSG_BODY_NULL)).throwable.message
        val apiServiceResMock = mockk<Response<AllRecipesDto>>()
        coEvery { apiServiceResMock.isSuccessful } returns true
        coEvery { apiServiceResMock.body() } returns null
        coEvery {
            recipesApiServices.getAllRecipes()
        } returns apiServiceResMock

        val result = testClass.getAllRecipes()
        TestCase.assertEquals(expected, (result as Result.Error).throwable.message)
        coVerify(exactly = 0) {
            allRecipesDtoMapper(any())
        }
    }

    @Test
    fun `GIVEN error path WHEN call getAllRecipes THEN return error`() = runTest {
        val apiServiceResMock = mockk<RuntimeException>()
        coEvery { apiServiceResMock.message } returns ERROR_MSG
        coEvery {
            recipesApiServices.getAllRecipes()
        } throws apiServiceResMock

        val result = testClass.getAllRecipes()
        TestCase.assertEquals(ERROR_MSG, (result as Result.Error).throwable.message)
        coVerify(exactly = 0) {
            allRecipesDtoMapper(any())
        }
    }

    @Test
    fun `GIVEN error path WHEN call getAllRecipes THEN return Uncaught exception`() = runTest {
        val runtimeException = mockk<Exception>()
        coEvery { recipesApiServices.getAllRecipes() } throws runtimeException
        val result = testClass.getAllRecipes()
        TestCase.assertEquals(
            ERROR_MSG_UNCAUGHT_EXCEPTION,
            (result as Result.Error).throwable.message
        )
        coVerify(exactly = 0) {
            allRecipesDtoMapper(any())
        }
    }

    private fun provideAllRecipeDtoSampleData() = AllRecipesDto(
        recipes = listOf(
            RecipesDto(
                id = ID,
                name = NAME,
                image = IMAGE,
                cuisine = CUISINE,
                ingredients = listOf(INGREDIENT_SALT, INGREDIENT_PEPPER, INGREDIENT_HONEY),
                instructions = listOf(INSTRUCTION_FIRST, INSTRUCTION_TWO),
                prepTimeMinutes = PREP_TIME,
                cookTimeMinutes = COOK_TIME,
                servings = SERVING,
                difficulty = DIFFICULTY,
                caloriesPerServing = CALORIE_PER_SERVING,
                tags = listOf(TAG1, TAG2),
                userId = USER_ID,
                rating = RATING,
                reviewCount = REVIEW_COUNT,
                mealType = listOf(MEAL_TYPE, MEAL_TYPE_TWO)
            )
        ),
        total = RECIPE_COUNT
    )

    private fun provideAllRecipeSampleData() = AllRecipes(
        recipes = listOf(
            Recipe(
                id = ID,
                name = NAME,
                image = IMAGE,
                cuisine = CUISINE,
                ingredients = listOf(INGREDIENT_SALT, INGREDIENT_PEPPER, INGREDIENT_HONEY),
                instructions = listOf(INSTRUCTION_FIRST, INSTRUCTION_TWO),
                prepTimeMinutes = PREP_TIME,
                cookTimeMinutes = COOK_TIME,
                servings = SERVING,
                difficulty = DIFFICULTY,
                caloriesPerServing = CALORIE_PER_SERVING,
                tags = listOf(TAG1, TAG2),
                userId = USER_ID,
                rating = RATING,
                reviewCount = REVIEW_COUNT,
                mealType = listOf(MEAL_TYPE, MEAL_TYPE_TWO)
            )
        ),
        total = RECIPE_COUNT
    )
}