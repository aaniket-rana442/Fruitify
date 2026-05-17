package com.example.fruitlify_app.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fruitlify_app.FruitRepository
import com.example.fruitlify_app.OrderStatus
import com.example.fruitlify_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOrdersScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Ongoing", "Completed", "Cancelled")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Orders") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            val filteredOrders = when (selectedTab) {
                0 -> FruitRepository.orders.filter { it.status == OrderStatus.ONGOING }
                1 -> FruitRepository.orders.filter { it.status == OrderStatus.COMPLETED }
                else -> FruitRepository.orders.filter { it.status == OrderStatus.CANCELLED }
            }

            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(filteredOrders) { order ->
                    OrderCard(order)
                }
            }
        }
    }
}

@Composable
fun OrderCard(order: com.example.fruitlify_app.Order) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Order ID: ${order.id}", fontWeight = FontWeight.Bold)
                Text(
                    text = order.status.name,
                    color = when (order.status) {
                        OrderStatus.ONGOING -> colorResource(id = R.color.green)
                        OrderStatus.COMPLETED -> Color.Blue
                        OrderStatus.CANCELLED -> Color.Red
                    },
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text("${order.date} | ${order.time}", color = Color.Gray, fontSize = 12.sp)
            Spacer(Modifier.height(8.dp))
            Text("${order.items.size} Items • Total: ₹${order.totalAmount}", fontWeight = FontWeight.Medium)
        }
    }
}
