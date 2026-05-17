package com.example.fruitlify_app.ui.theme.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fruitlify_app.FruitRepository
import com.example.fruitlify_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    var darkMode by remember { mutableStateOf(false) }
    var notifications by remember { mutableStateOf(true) }
    var saveOrderHistory by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text("Preferences", color = Color.Gray, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))

            SettingsSwitchItem(Icons.Default.History, "Save Order History", saveOrderHistory) { saveOrderHistory = it }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Others", color = Color.Gray, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))

            SettingsClickItem(Icons.Default.LocationOn, "Delivery Address") { }
            SettingsClickItem(Icons.Default.Language, "Language", "English") {  }
            SettingsClickItem(Icons.Default.HelpCenter, "Help & Support") {  }

            Spacer(modifier = Modifier.height(24.dp))
            
            TextButton(
                onClick = {
                    FruitRepository.setRole("Customer")
                    navController.navigate("role_selection") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Icon(Icons.Default.Logout, null, tint = Color.Red)
                    Spacer(Modifier.width(8.dp))
                    Text("Log Out", color = Color.Red, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun SettingsSwitchItem(icon: ImageVector, title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    ListItem(
        headlineContent = { Text(title) },
        leadingContent = { Icon(icon, null) },
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(checkedThumbColor = colorResource(R.color.green))
            )
        }
    )
}

@Composable
fun SettingsClickItem(icon: ImageVector, title: String, subtitle: String? = null, onClick: () -> Unit) {
    ListItem(
        modifier = Modifier.clickable { onClick() },
        headlineContent = { Text(title) },
        leadingContent = { Icon(icon, null) },
        supportingContent = subtitle?.let { { Text(it) } },
        trailingContent = { Icon(Icons.Default.ChevronRight, null) }
    )
}
