package com.example.yedustore1

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore(name = "settings")

class ThemeDataStore(private val context: Context) {

    companion object {
        val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
    }

    val isDarkTheme: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[DARK_THEME_KEY] ?: false  // false = tema claro por defecto
    }

    suspend fun saveThemePreference(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = isDark
        }
    }
}
