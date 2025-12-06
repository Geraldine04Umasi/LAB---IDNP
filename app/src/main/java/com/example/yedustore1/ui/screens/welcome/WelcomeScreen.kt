package com.example.yedustore1.ui.screens.welcome

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.yedustore1.viewmodel.FavoriteViewModel
import com.example.yedustore1.ui.screens.home.HomeScreen
import com.example.yedustore1.ui.screens.categories.CategoriesScreen
import com.example.yedustore1.ui.screens.favorites.FavoritesScreen
import com.example.yedustore1.ui.screens.cart.CartScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    nombre: String,
    onLogout: () -> Unit,
    onOpenSettings: () -> Unit,
    favoriteViewModel: FavoriteViewModel
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Yedu Store") },
                navigationIcon = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = "Cerrar sesión"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onOpenSettings) {
                        Icon(Icons.Filled.Settings, contentDescription = "Configuración")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(nombre) }
            composable("categories") { CategoriesScreen(favoriteViewModel) }
            composable("favorites") { FavoritesScreen(favoriteViewModel) }
            composable("cart") { CartScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf("home", "categories", "favorites", "cart")
    val icons = listOf(Icons.Filled.Home, Icons.Filled.Category, Icons.Filled.Favorite, Icons.Filled.ShoppingCart)
    val labels = listOf("Inicio", "Categorías", "Favoritos", "Carrito")

    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEachIndexed { index, route ->
            NavigationBarItem(
                selected = currentRoute == route,
                onClick = { navController.navigate(route) },
                icon = { Icon(icons[index], contentDescription = labels[index]) },
                label = { Text(labels[index]) }
            )
        }
    }
}
