package com.example.features.home.presentation.allRecipies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utility.Result
import com.example.features.home.domain.AllRecipes.usecase.AllRecipesUseCase
import com.example.features.home.presentation.allRecipies.AllRecipesIntent
import com.example.features.home.presentation.allRecipies.mapper.AllRecipesUiMapper
import com.example.features.home.presentation.allRecipies.uistate.AllRecipesUiState
import com.example.features.home.presentation.allRecipies.uistate.AllRecipesUiState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AllRecipeViewModel @Inject constructor(
    private val allRecipesUseCase: AllRecipesUseCase,
    private val allRecipesUiMapper: AllRecipesUiMapper
) : ViewModel() {

    private val _allRecipes = MutableStateFlow<AllRecipesUiState>(LOADING)
    val allRecipes: StateFlow<AllRecipesUiState> = _allRecipes

    fun handleEvent(event: AllRecipesIntent) {
        when (event) {
            AllRecipesIntent.LoadPage -> {
                viewModelScope.launch {
                    when (val res = allRecipesUseCase()) {
                        is Result.Error -> _allRecipes.value = ErrorUiState(
                            errorMessage = res.throwable.message
                        )

                        is Result.Success -> _allRecipes.update {
                            allRecipesUiMapper(res.data)
                        }

                    }
                }
            }

            AllRecipesIntent.RetryPageLoad -> _allRecipes.update {
                LOADING
            }
        }
    }
}