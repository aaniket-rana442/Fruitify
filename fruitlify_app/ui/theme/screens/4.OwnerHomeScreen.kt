package com.example.fruitlify_app.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.fruitlify_app.FruitRepository
import com.example.fruitlify_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerHomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Owner Dashboard", fontWeight = FontWeight.Bold,color=colorResource(R.color.green),fontSize = 28.sp) },
                actions = {
                    IconButton(onClick = { navController.navigate("owner_orders") }) {
                        Icon(painter = painterResource(id = R.drawable.order_icon), contentDescription = "Orders", modifier = Modifier.size(30.dp))
                    }
                    TextButton(
                        onClick = {
                            FruitRepository.setRole("Customer")
                            navController.navigate("role_selection") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    ) {
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            Icon(Icons.Default.Logout, null, tint = Color.Red)
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_fruit") },
                containerColor = colorResource(id = R.color.green)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Fruit", tint = Color.White)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            Text("Manage Inventory", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn {
                items(FruitRepository.fruits, key = { it.id }) { fruit ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            val painter = if (fruit.image.all { it.isDigit() }) {
                                painterResource(id = fruit.image.toInt())
                            } else {
                                rememberAsyncImagePainter(model = fruit.image)
                            }

                            Image(
                                painter = painter,
                                contentDescription = null,
                                modifier = Modifier.size(60.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(fruit.name, fontWeight = FontWeight.Bold)
                                Text("₹${fruit.price}/kg", color = colorResource(id = R.color.green))
                            }
                            IconButton(onClick = { FruitRepository.deleteFruit(fruit.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun preview4(){
    val navController = rememberNavController()

    OwnerHomeScreen(navController)
}
