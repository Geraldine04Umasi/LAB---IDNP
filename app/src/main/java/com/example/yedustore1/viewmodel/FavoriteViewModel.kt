package com.example.yedustore1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yedustore1.data.FavoriteProduct
import com.example.yedustore1.data.FavoritesRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: FavoritesRepository
) : ViewModel() {

    // Flow que expone la lista de favoritos para la UI
    val favorites = repository.favorites

    // Agregar un producto a favoritos
    fun addFavorite(product: FavoriteProduct) {
        viewModelScope.launch {
            repository.addFavorite(product)
        }
    }

    // Eliminar un único producto
    fun removeFavorite(product: FavoriteProduct) {
        viewModelScope.launch {
            repository.removeFavorite(product)
        }
    }

    // Borrar toda la lista
    fun clearAll() {
        viewModelScope.launch {
            repository.clearAll()
        }
    }
}
