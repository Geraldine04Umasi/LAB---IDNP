package com.example.yedustore1.data

import kotlinx.coroutines.flow.Flow

class FavoritesRepository(private val dao: FavoriteDao) {

    val favorites: Flow<List<FavoriteProduct>> = dao.getAllFavorites()

    suspend fun addFavorite(product: FavoriteProduct) {
        dao.insertFavorite(product)
    }

    suspend fun removeFavorite(product: FavoriteProduct) {
        dao.deleteFavorite(product)
    }

    suspend fun clearAll() {
        dao.deleteAll()
    }
}
