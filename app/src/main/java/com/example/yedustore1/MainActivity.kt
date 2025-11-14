package com.example.yedustore1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var themeDataStore: ThemeDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        themeDataStore = ThemeDataStore(this)

        setContent {

            // Leer tema desde DataStore
            val isDarkTheme by themeDataStore.isDarkTheme.collectAsState(initial = false)

            MyStoreTheme(darkTheme = isDarkTheme) {
                YeduStoreApp(
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = { newTheme ->
                        lifecycleScope.launch {
                            themeDataStore.saveThemePreference(newTheme)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun YeduStoreApp(
    isDarkTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(
                onLogin = { nombre, password ->
                    navController.navigate("welcome/$nombre/$password")
                },
                onRegisterClick = { navController.navigate("register") }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegister = { nombre ->
                    navController.navigate("welcome/$nombre/nuevo")
                },
                onCancel = { navController.popBackStack() }
            )
        }

        // 🔥 Nueva ruta SETTINGS
        composable("settings") {
            SettingsScreen(
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeToggle
            )
        }

        composable(
            route = "welcome/{nombre}/{codigo}",
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("codigo") { type = NavType.StringType }
            )
        ) { backStack ->
            val nombre = backStack.arguments?.getString("nombre") ?: ""

            WelcomeScreen(
                nombre = nombre,
                onLogout = { navController.popBackStack("login", false) },
                onOpenSettings = { navController.navigate("settings") }
            )
        }
    }
}

