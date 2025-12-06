package com.example.yedustore1.ui.screens.categories

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.yedustore1.viewmodel.FavoriteViewModel
import androidx.compose.ui.unit.dp


@Composable
fun CategoriesScreen(favoriteViewModel: FavoriteViewModel) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val categories = listOf("Mujer", "Hombre", "Niño", "Verano", "Invierno")

    if (selectedCategory == null) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Categorías", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(16.dp))

            categories.forEach { category ->
                Button(
                    onClick = { selectedCategory = category },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text(category)
                }
            }
        }
    } else {
        when (selectedCategory) {
            "Mujer" -> ProductListScreen(
                title = "Ropa para chicas",
                products = sampleWomenProducts(),
                onBack = { selectedCategory = null },
                favoriteViewModel = favoriteViewModel
            )

            else -> Text("Categoría próximamente disponible")
        }
    }
}
