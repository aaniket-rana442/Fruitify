package com.example.fruitlify_app.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun FruitDetailScreen(navController: NavController, fruitId: Int?) {
    val fruit = FruitRepository.fruits.find { it.id == fruitId } ?: return
    var quantity by remember { mutableStateOf(1.0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Handle image as either Resource ID (digit string) or URI
            val painter = if (fruit.image.all { it.isDigit() }) {
                painterResource(id = fruit.image.toIntOrNull() ?: R.drawable.apple)
            } else {
                rememberAsyncImagePainter(model = fruit.image)
            }

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(24.dp),
                contentScale = ContentScale.Fit
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(fruit.name, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, null, tint = Color(0xFFFFB800))
                    Text(fruit.rating.toString(), fontWeight = FontWeight.Bold)
                    Text(" (${fruit.reviewCount})", color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("₹${fruit.price}/kg", fontSize = 22.sp, color = colorResource(id = R.color.green), fontWeight = FontWeight.SemiBold)
            
            Spacer(modifier = Modifier.height(16.dp))

            Card(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)){
                Text(fruit.description, color = Color.Gray, modifier = Modifier.padding(10.dp))
            }
            
            
            Spacer(modifier = Modifier.weight(1f))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Quantity", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { if (quantity > 1) quantity -= 0.5 }) {
                        Icon(Icons.Default.Remove, null)
                    }
                    Text("$quantity kg", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    IconButton(onClick = { quantity += 0.5 }) {
                        Icon(Icons.Default.Add, null)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth().height(60.dp)){
                Button(
                    onClick = {
                        FruitRepository.addToCart(fruit, quantity)
                        navController.navigate("cart")
                    },
                    modifier = Modifier.height(50.dp).fillMaxSize(),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Add to Cart", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun preview8(){
    val navController = rememberNavController()

    FruitDetailScreen(navController,1)
}