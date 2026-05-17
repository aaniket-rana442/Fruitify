package com.example.fruitlify_app

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

enum class OrderStatus {
    ONGOING, COMPLETED, CANCELLED
}

data class Order(
    val id: String,
    val items: List<CartItem>,
    val totalAmount: Double,
    val status: OrderStatus,
    val date: String,
    val time: String,
    val address: String
)

data class CartItem(
    val fruit: Fruit,
    val quantity: Double
)

object FruitRepository {
    private const val PREFS_NAME = "fruitify_persistent_storage_v4"
    private const val KEY_FRUITS = "fruits_data"
    private const val KEY_ORDERS = "orders_data"
    private const val KEY_CART = "cart_data"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_ROLE = "user_role"
    
    private lateinit var prefs: SharedPreferences
    private val gson = Gson()

    val fruits = mutableStateListOf<Fruit>()
    val cartItems = mutableStateListOf<CartItem>()
    val orders = mutableStateListOf<Order>()
    val userName = mutableStateOf("Aniket Rana")
    val userRole = mutableStateOf("Customer")

    fun init(context: Context) {
        if (::prefs.isInitialized) return
        prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        loadData()
    }

    private fun loadData() {
        try {
            val fruitsJson = prefs.getString(KEY_FRUITS, null)
            if (!fruitsJson.isNullOrEmpty()) {
                val type = object : TypeToken<List<Fruit>>() {}.type
                val savedFruits: List<Fruit> = gson.fromJson(fruitsJson, type)
                fruits.clear()
                fruits.addAll(savedFruits)
            } else {
                resetFruitsToDefault()
            }

            val ordersJson = prefs.getString(KEY_ORDERS, null)
            if (!ordersJson.isNullOrEmpty()) {
                val type = object : TypeToken<List<Order>>() {}.type
                orders.clear()
                orders.addAll(gson.fromJson(ordersJson, type))
            }

            val cartJson = prefs.getString(KEY_CART, null)
            if (!cartJson.isNullOrEmpty()) {
                val type = object : TypeToken<List<CartItem>>() {}.type
                cartItems.clear()
                cartItems.addAll(gson.fromJson(cartJson, type))
            }

            userName.value = prefs.getString(KEY_USER_NAME, "Aniket Rana") ?: "Aniket Rana"
            userRole.value = prefs.getString(KEY_USER_ROLE, "Customer") ?: "Customer"
        } catch (e: Exception) {
            resetFruitsToDefault()
        }
    }

    private fun resetFruitsToDefault() {
        fruits.clear()
        fruits.addAll(listOf(
            Fruit(1, "Apple", "120", 4.5, 120, "Fresh apples from Himachal", R.drawable.apple.toString(), FruitCategory.ORGANIC),
            Fruit(2, "Banana", "60", 4.2, 85, "Ripe yellow bananas", R.drawable.banana.toString(), FruitCategory.SEASONAL),
            Fruit(3, "Mango", "150", 4.8, 200, "Sweet Alphonso mangoes", R.drawable.mango.toString(), FruitCategory.SEASONAL),
            Fruit(4, "Grapes", "100", 4.3, 90, "Seedless green grapes", R.drawable.grapes.toString(), FruitCategory.IMPORTED)
        ))
        saveData()
    }

    fun saveData() {
        if (!::prefs.isInitialized) return
        prefs.edit().apply {
            putString(KEY_FRUITS, gson.toJson(fruits.toList()))
            putString(KEY_ORDERS, gson.toJson(orders.toList()))
            putString(KEY_CART, gson.toJson(cartItems.toList()))
            putString(KEY_USER_NAME, userName.value)
            putString(KEY_USER_ROLE, userRole.value)
            apply()
        }
    }

    fun addFruit(fruit: Fruit) {
        fruits.add(fruit)
        saveData()
    }

    fun deleteFruit(fruitId: Int) {
        fruits.removeAll { it.id == fruitId }
        cartItems.removeAll { it.fruit.id == fruitId }
        saveData()
    }

    fun addToCart(fruit: Fruit, quantity: Double) {
        val index = cartItems.indexOfFirst { it.fruit.id == fruit.id }
        if (index != -1) {
            cartItems[index] = cartItems[index].copy(quantity = cartItems[index].quantity + quantity)
        } else {
            cartItems.add(CartItem(fruit, quantity))
        }
        saveData()
    }

    fun removeFromCart(cartItem: CartItem) {
        cartItems.removeAll { it.fruit.id == cartItem.fruit.id }
        saveData()
    }

    fun placeOrder(address: String, date: String, time: String) {
        val total = cartItems.sumOf { 
            val priceNum = it.fruit.price.filter { c -> c.isDigit() || c == '.' }.toDoubleOrNull() ?: 0.0
            priceNum * it.quantity 
        }
        val newOrder = Order(
            id = "#FRU${(10000..99999).random()}",
            items = cartItems.toList(),
            totalAmount = total,
            status = OrderStatus.ONGOING,
            date = date,
            time = time,
            address = address
        )
        orders.add(newOrder)
        cartItems.clear()
        saveData()
    }

    fun cancelOrder(orderId: String) {
        val index = orders.indexOfFirst { it.id == orderId }
        if (index != -1) {
            orders[index] = orders[index].copy(status = OrderStatus.CANCELLED)
            saveData()
        }
    }

    fun setRole(role: String) {
        userRole.value = role
        saveData()
    }

    fun setUserName(name: String) {
        userName.value = name
        saveData()
    }
}
