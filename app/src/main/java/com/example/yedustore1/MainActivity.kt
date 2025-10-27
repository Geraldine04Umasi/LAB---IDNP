package com.example.yedustore1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YeduStoreApp()
        }
    }
}

@Composable
fun YeduStoreApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLogin = { nombre, codigo ->
                    navController.navigate("welcome/$nombre/$codigo")
                },
                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }

        // Nueva ruta: registro
        composable("register") {
            RegisterScreen(
                onRegister = { nombre ->
                    // Llevamos al welcome al registrarse
                    navController.navigate("welcome/$nombre/nuevo")
                },
                onCancel = { navController.popBackStack() }
            )
        }

        composable(
            route = "welcome/{nombre}/{codigo}",
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("codigo") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val codigo = backStackEntry.arguments?.getString("codigo") ?: ""
            WelcomeScreen(nombre, codigo) {
                navController.popBackStack("login", inclusive = false)
            }
        }
    }
}