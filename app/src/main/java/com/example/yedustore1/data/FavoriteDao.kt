package com.example.yedustore1.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert
    suspend fun insertFavorite(product: FavoriteProduct)

    @Delete
    suspend fun deleteFavorite(product: FavoriteProduct)

    @Query("SELECT * FROM favorite_products ORDER BY id DESC")
    fun getAllFavorites(): Flow<List<FavoriteProduct>>

    @Query("DELETE FROM favorite_products")
    suspend fun deleteAll()
}
