package com.example.features.home.presentation.recipieDetail.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.core.utility.Result
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.features.home.domain.recipeDetail.entities.Recipe
import com.example.features.home.domain.recipeDetail.usecase.RecipeDetailUseCase
import com.example.features.home.presentation.recipieDetail.intent.RecipeDetailIntent
import com.example.features.home.presentation.recipieDetail.mapper.RecipeUiMapper
import com.sample.presentation.feature.recipedetails.uistate.RecipeDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val recipeDetailUseCase: RecipeDetailUseCase,
    private val stateHandle: SavedStateHandle,
    private val recipeUiMapper: RecipeUiMapper
) : ViewModel() {

    private val _recipeDetail = MutableStateFlow<RecipeDetailUiState>(RecipeDetailUiState.LOADING)
    val recipeDetail: StateFlow<RecipeDetailUiState> = _recipeDetail

    fun handleEvents(event: RecipeDetailIntent) {
        when (event) {
            RecipeDetailIntent.LoadPage -> {
                viewModelScope.launch {
                    val data = stateHandle.get<String>("recipeId")
                    data?.let {
                        when (val res = recipeDetailUseCase(data)) {
                            is Result.Error -> _recipeDetail.update {
                                RecipeDetailUiState.ErrorState(
                                    res.throwable.message
                                )
                            }

                            is Result.Success<*> -> _recipeDetail.update {
                                recipeUiMapper(res.data as Recipe)
                            }
                        }
                    }
                }
            }
        }
    }
}