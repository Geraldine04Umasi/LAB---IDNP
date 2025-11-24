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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.yedustore1.viewmodel.FavoriteViewModel
import com.example.yedustore1.data.FavoriteProduct



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
                            contentDescription = "Cerrar sesión",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                },

                actions = {
                    // Botón para abrir la pantalla de ajustes (DataStore)
                    IconButton(onClick = onOpenSettings) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Configuración",
                            tint = MaterialTheme.colorScheme.primary
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
                composable("categories") { CategoriesScreen(favoriteViewModel) }
                composable("favorites") { FavoritesScreen(favoriteViewModel) }
                composable("cart") { CartScreen() }
            }
        }
    )
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
fun CategoriesScreen(favoriteViewModel: FavoriteViewModel) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val categories = listOf("Mujer", "Hombre", "Niño", "Verano", "Invierno")

    if (selectedCategory == null) {
        // Pantalla inicial de categorías
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Categorías",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            categories.forEach { category ->
                Button(
                    onClick = { selectedCategory = category },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(category)
                }
            }
        }
    } else {
        // Mostrar productos de la categoría seleccionada
        when (selectedCategory) {
            "Mujer" -> ProductListScreen(
                title = "Ropa para chicas",
                products = sampleWomenProducts(),
                onBack = { selectedCategory = null },
                favoriteViewModel = favoriteViewModel
            )

            else -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Próximamente implementado en categoría $selectedCategory 🛍️",
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { selectedCategory = null }) {
                        Text("Volver")
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    title: String,
    products: List<Product>,
    onBack: () -> Unit,
    favoriteViewModel: FavoriteViewModel
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(title) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                }
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(products) { product ->
                ProductCard(product, favoriteViewModel)
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, favoriteViewModel: FavoriteViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = Icons.Filled.Checkroom,
                contentDescription = product.name,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 12.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, style = MaterialTheme.typography.titleMedium)
                Text(product.description, style = MaterialTheme.typography.bodyMedium)
                Text("Precio: $${product.price}", color = MaterialTheme.colorScheme.primary)
                Text("Talla: ${product.size}", style = MaterialTheme.typography.bodySmall)
            }

            IconButton(
                onClick = {
                    favoriteViewModel.addFavorite(
                        FavoriteProduct(
                            name = product.name,
                            price = product.price,
                            size = product.size
                        )
                    )
                }
            ) {
                Icon(Icons.Filled.Favorite, contentDescription = "Agregar a favoritos")
            }
        }
    }
}


data class Product(
    val name: String,
    val description: String,
    val price: Double,
    val size: String
)

fun sampleWomenProducts(): List<Product> = listOf(
    Product("Vestido floral", "Vestido de algodón con estampado de flores", 89.99, "M"),
    Product("Blusa elegante", "Blusa satinada con cuello en V", 59.49, "S"),
    Product("Falda plisada", "Falda midi plisada color crema", 69.99, "M"),
    Product("Pantalón palazzo", "Pantalón ancho de lino", 75.50, "L"),
    Product("Top de encaje", "Top delicado con detalles de encaje", 39.90, "S"),
    Product("Chaqueta de mezclilla", "Clásica chaqueta azul de jean", 99.00, "M"),
    Product("Abrigo largo", "Abrigo de lana beige con cinturón", 120.00, "L"),
    Product("Camisa blanca", "Camisa formal de algodón orgánico", 49.99, "M"),
    Product("Pantalones cortos", "Shorts de lino beige", 45.00, "S"),
    Product("Vestido negro", "Vestido corto elegante para noche", 95.99, "M"),
    Product("Suéter tejido", "Suéter cálido de hilo natural", 70.00, "M"),
    Product("Blazer clásico", "Blazer estructurado color crema", 110.00, "M"),
    Product("Falda corta", "Falda denim azul oscuro", 55.00, "S"),
    Product("Chaleco acolchado", "Chaleco liviano de temporada", 80.00, "M"),
    Product("Camiseta básica", "Camiseta de algodón ecológico", 35.00, "M"),
    Product("Pijama suave", "Pijama de algodón orgánico", 60.00, "L"),
    Product("Pantalón deportivo", "Joggers cómodos de algodón", 50.00, "M"),
    Product("Cárdigan largo", "Cárdigan beige de punto grueso", 85.00, "M"),
    Product("Bufanda tejida", "Bufanda artesanal de alpaca", 30.00, "U"),
    Product("Chaqueta de cuero", "Chaqueta sintética estilo biker", 130.00, "M")
)


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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
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
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Eliminar"
                                )
                            }
                        }
                    }
                }
            }
        }
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
