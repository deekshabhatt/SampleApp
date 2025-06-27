package com.example.features.home.presentation.recipieDetail.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.features.home.presentation.recipieDetail.intent.RecipeDetailIntent
import com.example.features.home.presentation.recipieDetail.viewmodel.RecipeDetailViewModel
import com.sample.presentation.feature.recipedetails.uistate.RecipeDetailUiState

@Composable
fun RecipeDetailScreen(
    innerPadding: PaddingValues,
    viewModel: RecipeDetailViewModel = hiltViewModel()
) {
    viewModel.handleEvents(RecipeDetailIntent.LoadPage)
    val data by viewModel.recipeDetail.collectAsState()
    when (data) {
        is RecipeDetailUiState.DataLoaded -> {
            RecipeDetailScreenContent(
                data as RecipeDetailUiState.DataLoaded,
                innerPadding
            )
        }

        RecipeDetailUiState.LOADING -> Log.e("TAG","")
        is RecipeDetailUiState.ErrorState -> {
            Text(
                text = (data as RecipeDetailUiState.ErrorState).errorMessage
                    ?: "error"
            )
            Button(
                onClick = {
                    viewModel.handleEvents(RecipeDetailIntent.LoadPage)
                }
            ) {
                Text(text = "retry")
            }
        }
    }
}

@Composable
fun RecipeDetailScreenContent(
    data: RecipeDetailUiState.DataLoaded,
    innerPadding: PaddingValues
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val imageHeight = screenHeight * 0.3f
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = data.imageUrl,
                contentDescription = data.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = data.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = data.ingredients,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

