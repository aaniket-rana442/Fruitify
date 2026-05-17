package com.example.fruitlify_app.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
fun OwnerOrdersScreen(navController: NavController) {
    val context = LocalContext.current
    val orders = FruitRepository.orders

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Orders") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->
        if (orders.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No orders placed yet.", color = Color.Gray)
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
                items(orders, key = { it.id }) { order ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Column {
                                    Text("Order ID: ${order.id}", fontWeight = FontWeight.Bold)
                                    Text("Address: ${order.address}", fontSize = 12.sp, color = Color.Gray)
                                }
                                if (order.status == OrderStatus.ONGOING) {
                                    IconButton(onClick = { 
                                        FruitRepository.cancelOrder(order.id)
                                        Toast.makeText(context, "Order Cancelled", Toast.LENGTH_SHORT).show()
                                    }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Cancel Order", tint = Color.Red)
                                    }
                                }
                            }
                            Spacer(Modifier.height(8.dp))
                            order.items.forEach { item ->
                                Text("• ${item.fruit.name} (${item.quantity} kg)", fontSize = 14.sp)
                            }
                            Divider(Modifier.padding(vertical = 8.dp))
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Total: ₹${order.totalAmount}", fontWeight = FontWeight.Bold)
                                Text(
                                    text = order.status.name,
                                    color = when (order.status) {
                                        OrderStatus.ONGOING -> colorResource(id = R.color.green)
                                        OrderStatus.COMPLETED -> Color.Blue
                                        OrderStatus.CANCELLED -> Color.Red
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
