package com.example.fruitlify_app.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fruitlify_app.FruitRepository
import com.example.fruitlify_app.R

@Composable
fun RoleSelectionScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to Fruitify",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.green)
        )
        Text(
            text = "Please select your role",
            fontSize = 20.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(40.dp))
        
        Button(
            onClick = { 
                FruitRepository.setRole("Owner")
                navController.navigate("owner_login") 
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green))
        ) {
            Text("Shop Owner", fontSize = 20.sp)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedButton(
            onClick = { 
                FruitRepository.setRole("Customer")
                navController.navigate("home") 
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = colorResource(id = R.color.green))
        ) {
            Text("Customer / Buyer", fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun preview2(){
    val navController = rememberNavController()

    RoleSelectionScreen(navController)
}