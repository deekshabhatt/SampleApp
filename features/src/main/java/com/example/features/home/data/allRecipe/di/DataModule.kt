package com.example.dummyapp.di


import com.example.core.network.contract.ApiExecutor
import com.example.features.home.data.allRecipe.impl.AllRecipesRepositoryImpl
import com.example.core.network.impl.ApiExecutorImpl
import com.example.features.home.data.services.RecipesApiServices
import com.example.features.home.domain.AllRecipes.repository.AllRecipesRepository
import com.example.features.home.data.allRecipe.mapperDto.AllRecipesDtoMapper
import com.example.features.home.data.recipeDetail.RecipeDtoMapper
import com.example.features.home.data.recipeDetail.impl.RecipeDetailRepositoryImpl
import com.example.features.home.domain.recipeDetail.repository.RecipeDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class DataModule {
    @Provides
    fun provideRecipesApiService(retrofit: Retrofit): RecipesApiServices {
        return retrofit.create(RecipesApiServices::class.java)
    }

    @Provides
    fun provideAllRecipesRepository(
        recipesApiServices: RecipesApiServices,
        allRecipesDtoMapper: AllRecipesDtoMapper,
        apiExecutor: ApiExecutor
    ): AllRecipesRepository {
        return AllRecipesRepositoryImpl(
            recipesApiServices,
            allRecipesDtoMapper,
            apiExecutor
        )
    }

    @Provides
    fun provideRecipeDetailRepository(
        recipesApiServices: RecipesApiServices,
        recipeDtoMapper: RecipeDtoMapper,
        apiExecutor: ApiExecutor
    ): RecipeDetailRepository {
        return RecipeDetailRepositoryImpl(
            recipesApiServices,
            recipeDtoMapper,
            apiExecutor
        )
    }


    @Provides
    fun provideApiExecutor(): ApiExecutor {
        return ApiExecutorImpl()
    }

}