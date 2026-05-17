package com.example.fruitlify_app.ui.theme.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fruitlify_app.FruitRepository
import com.example.fruitlify_app.R
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(navController: NavController) {
    val context = LocalContext.current
    
    // Get the address from the map selection screen if available
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val selectedAddress = savedStateHandle?.get<String>("selected_address")
    
    var address by remember { mutableStateOf("123, Green Street, Bangalore - 560001") }
    
    LaunchedEffect(selectedAddress) {
        selectedAddress?.let { address = it }
    }

    var selectedDate by remember { mutableStateOf("Select Date") }
    var selectedTime by remember { mutableStateOf("Select Time") }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth -> selectedDate = "$dayOfMonth/${month + 1}/$year" },
        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute -> selectedTime = String.format("%02d:%02d", hour, minute) },
        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkout") },
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
                .padding(16.dp)
        ) {
            Text("Delivery Address", fontWeight = FontWeight.Bold)
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = colorResource(id = R.color.green))
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Home", fontWeight = FontWeight.Bold)
                        Text(address, color = Color.Gray, fontSize = 14.sp)
                    }
                    TextButton(onClick = { 
                        navController.navigate("map_selection")
                    }) {
                        Text("Change", color = colorResource(id = R.color.green))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Delivery Date", fontWeight = FontWeight.Bold)
            OutlinedCard(
                onClick = { datePickerDialog.show() },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(selectedDate, modifier = Modifier.weight(1f))
                    Icon(Icons.Default.CalendarMonth, null)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Delivery Time Slot", fontWeight = FontWeight.Bold)
            OutlinedCard(
                onClick = { timePickerDialog.show() },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(selectedTime, modifier = Modifier.weight(1f))
                    Icon(Icons.Default.Schedule, null)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (selectedDate == "Select Date" || selectedTime == "Select Time") {
                        Toast.makeText(context, "Please select date and time", Toast.LENGTH_SHORT).show()
                    } else {
                        FruitRepository.placeOrder(address, selectedDate, selectedTime)
                        navController.navigate("success") {
                            popUpTo("home") { inclusive = false }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Place Order", fontSize = 18.sp)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun preview9(){
    val navController = rememberNavController()

    CheckoutScreen(navController)
}