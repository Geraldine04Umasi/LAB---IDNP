package com.example.yedustore1.ui.screens.categories

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yedustore1.data.FavoriteProduct
import com.example.yedustore1.viewmodel.FavoriteViewModel

@Composable
fun ProductCard(product: Product, favoriteViewModel: FavoriteViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = Icons.Filled.Checkroom,
                contentDescription = product.name,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp).padding(end = 12.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, style = MaterialTheme.typography.titleMedium)
                Text(product.description)
                Text("Precio: $${product.price}")
                Text("Talla: ${product.size}")
            }

            IconButton(onClick = {
                favoriteViewModel.addFavorite(
                    FavoriteProduct(
                        name = product.name,
                        price = product.price,
                        size = product.size
                    )
                )
            }) {
                Icon(Icons.Filled.Favorite, contentDescription = "Agregar a favoritos")
            }
        }
    }
}
