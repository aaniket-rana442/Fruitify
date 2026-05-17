package com.example.fruitlify_app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fruitlify_app.ui.theme.screens.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") { SplashScreen(navController) }
        composable("role_selection") { RoleSelectionScreen(navController) }
        composable("owner_login") { OwnerLoginScreen(navController) }
        
        // Customer Flow
        composable("home") { HomeScreen(navController) }
        composable("details/{fruitId}") { backStackEntry ->
            val fruitId = backStackEntry.arguments?.getString("fruitId")?.toIntOrNull()
            FruitDetailScreen(navController, fruitId)
        }
        composable("cart") { CartScreen(navController) }
        composable("checkout") { CheckoutScreen(navController) }
        composable("map_selection") { MapSelectionScreen(navController) }
        composable("success") { OrderSuccessScreen(navController) }
        composable("categories") { CategoriesScreen(navController) }
        composable("orders") { MyOrdersScreen(navController) }
        composable("tracking") { OrderTrackingScreen(navController) }
        composable("profile") { ProfileScreen(navController) }

        // Owner Flow
        composable("owner_home") { OwnerHomeScreen(navController) }
        composable("add_fruit") { AddFruitScreen(navController) }
        composable("owner_orders") { OwnerOrdersScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
    }
}
