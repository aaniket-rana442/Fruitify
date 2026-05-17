package com.example.fruitlify_app

val fruitList = listOf(
    Fruit(
        id = 1,
        name = "Apple",
        price = "120",
        rating = 4.5,
        reviewCount = 120,
        description = "Fresh and crunchy red apples from the farms of Himachal. Perfect for a healthy snack.",
        image = R.drawable.apple.toString(),
        category = FruitCategory.ORGANIC
    ),
    Fruit(
        id = 2,
        name = "Banana",
        price = "60",
        rating = 4.2,
        reviewCount = 85,
        description = "Ripe yellow bananas, rich in potassium and energy. Great for smoothies and snacks.",
        image = R.drawable.banana.toString(),
        category = FruitCategory.SEASONAL
    ),
    Fruit(
        id = 3,
        name = "Mango",
        price = "100",
        rating = 4.8,
        reviewCount = 200,
        description = "Sweet, juicy and handpicked Alphonso mangoes. The king of fruits is here.",
        image = R.drawable.mango.toString(),
        category = FruitCategory.SEASONAL
    ),
    Fruit(
        id = 4,
        name = "Grapes",
        price = "150",
        rating = 4.3,
        reviewCount = 90,
        description = "Fresh green seedless grapes, sweet and slightly tangy. Perfect for fruit salads.",
        image = R.drawable.grapes.toString(),
        category = FruitCategory.IMPORTED
    )
)
