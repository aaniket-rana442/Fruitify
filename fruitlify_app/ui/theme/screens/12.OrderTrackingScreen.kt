package com.example.fruitlify_app.ui.theme.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
import com.example.fruitlify_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTrackingScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Track Order") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text("Order ID: #FRU12345", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Estimated Delivery: 20 May 2024", color = Color.Gray)
            
            Spacer(modifier = Modifier.height(32.dp))
            
            TrackingStep("Order Placed", "20 May 2024 | 09:00 AM", true)
            TrackingLine(true)
            TrackingStep("Packed", "20 May 2024 | 10:15 AM", true)
            TrackingLine(true)
            TrackingStep("Out for Delivery", "Pending", false)
            TrackingLine(false)
            TrackingStep("Delivered", "Pending", false)
            
            Spacer(modifier = Modifier.weight(1f))
            val context = LocalContext.current
            Button(
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_DIAL,
                        Uri.parse("tel:7986545471")
                    )

                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green))
            ) {
                Text("Contact Delivery Partner", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TrackingStep(title: String, time: String, isCompleted: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    if (isCompleted) colorResource(id = R.color.green) else Color.LightGray,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted) {
                Icon(Icons.Default.Check, null, modifier = Modifier.size(16.dp), tint = Color.White)
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(title, fontWeight = FontWeight.SemiBold)
            Text(time, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun TrackingLine(isCompleted: Boolean) {
    Box(
        modifier = Modifier
            .padding(start = 11.dp)
            .width(2.dp)
            .height(40.dp)
            .background(if (isCompleted) colorResource(id = R.color.green) else Color.LightGray)
    )
}
