package com.example.features.home.presentation.allRecipies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.features.home.presentation.allRecipies.model.RecipeCardData

@Composable
fun RecipeCard(
    recipeCard: RecipeCardData,
    cardRoundedCornerShape: Shape = RoundedCornerShape(10.dp)
) {
    Card(
        modifier = recipeCard.modifier
            .fillMaxWidth()
            .clickable {
                recipeCard.callback?.invoke(recipeCard.id)
            },
        shape = cardRoundedCornerShape,
        elevation = CardDefaults.cardElevation(
            pressedElevation = 5.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                model = recipeCard.imageUrl,
                contentDescription = "title",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(5.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = recipeCard.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1
                )

                Text(
                    text = recipeCard.cuisine,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 1
                )

                Text(
                    text = recipeCard.difficulty,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewRecipeCard() {
    RecipeCard(
        RecipeCardData(
            id = "1",
            name = "Chicken Biryani",
            imageUrl = "https://cdn.pixabay.com/photo/2016/06/15/19/09/food-145969",
            cookingTime = "30 Min",
            difficulty = "Easy",
            cuisine = "Indian",
            modifier = Modifier.padding(10.dp)
        )
    )
}