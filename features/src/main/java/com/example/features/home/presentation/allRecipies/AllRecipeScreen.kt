package com.example.features.home.presentation.allRecipies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.features.home.presentation.allRecipies.uistate.AllRecipesUiState
import com.example.features.home.presentation.allRecipies.model.RecipeCardData
import com.example.features.home.presentation.allRecipies.navigation.NavigationItem
import com.example.features.home.presentation.allRecipies.viewmodel.AllRecipeViewModel

@Composable
fun AllRecipeScreen(
    innerPadding: PaddingValues,
    navController: NavHostController,
    homeViewModel: AllRecipeViewModel = hiltViewModel()
) {
    homeViewModel.handleEvent(AllRecipesIntent.LoadPage)
    val data by homeViewModel.allRecipes.collectAsState()
    val sendIntent by rememberUpdatedState(homeViewModel::handleEvent)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(innerPadding)
    ) {
        when (data) {
            is AllRecipesUiState.DataLoadedUiState -> {
                ListRecipe(data as AllRecipesUiState.DataLoadedUiState, navController)
            }

            AllRecipesUiState.LOADING -> Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            is AllRecipesUiState.ErrorUiState -> {
                Text(
                    text = (data as AllRecipesUiState.ErrorUiState).errorMessage ?: ""
                )
                Button(
                    onClick = {
                        sendIntent(AllRecipesIntent.RetryPageLoad)
                    }
                ) {
                    Text(text = "retry")
                }
            }
        }
    }
}


@Composable
fun ListRecipe(data: AllRecipesUiState.DataLoadedUiState, navController: NavHostController) {
    LazyColumn(
        modifier = Modifier.padding(10.dp)
    ) {
        items(data.recipesList) {
            RecipeCard(
                RecipeCardData(
                    id = it.id,
                    name = it.name,
                    imageUrl = it.imageUrl,
                    cookingTime = it.cookingTime,
                    difficulty = it.difficulty,
                    cuisine = it.cuisine,
                    modifier = Modifier.padding(10.dp),
                    callback = { recipeId ->
                        navController.navigate(NavigationItem.RecipeDetail.recipeId(recipeId))
                    }
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
