package com.example.yedustore1

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(nombre: String, onLogout: () -> Unit) {
    var isDarkTheme by rememberSaveable { mutableStateOf(false) }

    MyStoreTheme(darkTheme = isDarkTheme) {
        val navController = rememberNavController()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Yedu Store") },
                    actions = {
                        IconButton(onClick = { isDarkTheme = !isDarkTheme }) {
                            Icon(
                                imageVector = if (isDarkTheme) Icons.Filled.WbSunny else Icons.Filled.DarkMode,
                                contentDescription = "Cambiar tema",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { onLogout() }) {
                            Icon(
                                imageVector = Icons.Filled.ExitToApp,
                                contentDescription = "Cerrar sesión",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(navController)
            },
            content = { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("home") { HomeScreen(nombre) }
                    composable("categories") { CategoriesScreen() }
                    composable("favorites") { FavoritesScreen() }
                    composable("cart") { CartScreen() }
                }
            }
        )
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

@Composable
fun HomeScreen(nombre: String) {
    var expanded by remember { mutableStateOf(false) }
    val circleSize by animateDpAsState(
        targetValue = if (expanded) 150.dp else 60.dp,
        label = "circleAnimation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bienvenido $nombre 👋",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Moda con alma",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Yedu Store es una tienda comprometida con la moda sostenible y el estilo consciente. " +
                    "Aquí encontrarás prendas únicas hechas con amor y respeto por el planeta.",
            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        val circleColor = MaterialTheme.colorScheme.primary

        Canvas(
            modifier = Modifier.size(circleSize)
        ) {
            drawCircle(color = circleColor)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { expanded = !expanded }) {
            Text(if (expanded) "Reducir círculo" else "Expandir círculo")
        }
    }
}

@Composable
fun CategoriesScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "Categorías de ropa próximamente implementadas 👕👗",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FavoritesScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "Tus prendas favoritas aparecerán aquí",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CartScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "Carrito de compras próximamente disponible",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MyStoreTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFF90CAF9),
            secondary = Color(0xFF80DEEA),
            background = Color(0xFF121212),
            surface = Color(0xFF1E1E1E),
            error = Color(0xFFCF6679),
            onBackground = Color.White,
            onSurface = Color.White
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF1976D2),
            secondary = Color(0xFF03A9F4),
            background = Color(0xFFE3F2FD),
            surface = Color.White,
            error = Color(0xFFD32F2F),
            onBackground = Color.Black,
            onSurface = Color.Black
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
