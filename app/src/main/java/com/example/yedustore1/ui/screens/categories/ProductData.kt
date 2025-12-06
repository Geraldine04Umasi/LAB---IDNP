package com.example.yedustore1.ui.screens.categories

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
