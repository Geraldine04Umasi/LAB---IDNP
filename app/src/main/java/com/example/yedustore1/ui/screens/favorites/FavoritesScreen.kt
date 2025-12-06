package com.example.yedustore1.ui.screens.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yedustore1.viewmodel.FavoriteViewModel

@Composable
fun FavoritesScreen(favoriteViewModel: FavoriteViewModel) {

    val favorites by favoriteViewModel.favorites.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Mis Favoritos", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        if (favorites.isEmpty()) {
            Text("No hay productos favoritos aún.")
        } else {
            LazyColumn {
                items(favorites) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    ) {
                        Row(modifier = Modifier.padding(16.dp)) {

                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.name)
                                Text("Precio: $${item.price}")
                                Text("Talla: ${item.size}")
                            }

                            IconButton(onClick = {
                                favoriteViewModel.removeFavorite(item)
                            }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}
