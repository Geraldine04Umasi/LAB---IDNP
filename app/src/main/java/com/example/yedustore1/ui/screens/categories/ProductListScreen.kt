package com.example.yedustore1.ui.screens.categories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.example.yedustore1.viewmodel.FavoriteViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    title: String,
    products: List<Product>,
    onBack: () -> Unit,
    favoriteViewModel: FavoriteViewModel
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(title) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                }
            }
        )

        LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            items(products) { product ->
                ProductCard(product, favoriteViewModel)
            }
        }
    }
}
