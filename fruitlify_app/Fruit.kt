package com.example.fruitlify_app

enum class FruitCategory {
    ALL, SEASONAL, IMPORTED, ORGANIC
}

data class Fruit(
    val id: Int,
    val name: String,
    val price: String,
    val rating: Double = 4.0,
    val reviewCount: Int = 0,
    val description: String,
    val image: String, // Storing as String (URI or Drawable name) to support "uploads"
    val category: FruitCategory = FruitCategory.ALL
)
