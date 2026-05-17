package com.example.fruitlify_app.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fruitlify_app.Fruit
import com.example.fruitlify_app.FruitCategory
import com.example.fruitlify_app.FruitRepository
import com.example.fruitlify_app.R
import kotlinx.coroutines.launch
import androidx.compose.ui.zIndex
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(FruitCategory.ALL) }
    var showSearchSuggestions by remember { mutableStateOf(false) }

    val filteredFruits = FruitRepository.fruits.filter {
        (selectedCategory == FruitCategory.ALL || it.category == selectedCategory) &&
        it.name.contains(searchQuery, ignoreCase = true)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet (modifier = Modifier.width(300.dp)){
                DrawerHeader()
                Spacer(Modifier.height(12.dp))
                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = true,
                    onClick = { scope.launch { drawerState.close() } },
                    icon = { Icon(Icons.Default.Home, null) }
                )
                NavigationDrawerItem(
                    label = { Text("My Orders") },
                    selected = false,
                    onClick = { 
                        scope.launch { drawerState.close() }
                        navController.navigate("orders") 
                    },
                    icon = { Icon(Icons.Default.ListAlt, null) }
                )
                NavigationDrawerItem(
                    label = { Text("Cart") },
                    selected = false,
                    onClick = { 
                        scope.launch { drawerState.close() }
                        navController.navigate("cart") 
                    },
                    icon = { Icon(Icons.Default.ShoppingCart, null) }
                )
                NavigationDrawerItem(
                    label = { Text("Categories") },
                    selected = false,
                    onClick = { 
                        scope.launch { drawerState.close() }
                        navController.navigate("categories") 
                    },
                    icon = { Icon(Icons.Default.Category, null) }
                )
                NavigationDrawerItem(
                    label = { Text("Settings") },
                    selected = false,
                    onClick = { 
                        scope.launch { drawerState.close() }
                        navController.navigate("settings") 
                    },
                    icon = { Icon(Icons.Default.Settings, null) }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Fruitify", fontWeight = FontWeight.Bold,color=colorResource(R.color.green)) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("cart") }) {
                            BadgedBox(badge = {
                                if (FruitRepository.cartItems.isNotEmpty()) {
                                    Badge { Text(FruitRepository.cartItems.size.toString()) }
                                }
                            }) {
                                Icon(Icons.Default.ShoppingCart, "Cart")
                            }
                        }
                    }
                )
            },
            bottomBar = { BottomNavigationBar(navController) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                // Search Bar with Suggestions
                Box {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { 
                            searchQuery = it
                            showSearchSuggestions = it.isNotEmpty()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Search fruits...") },
                        leadingIcon = { Icon(Icons.Default.Search, null) },
                        shape = RoundedCornerShape(12.dp)
                    )
                    
                    if (showSearchSuggestions) {
                        val suggestions = FruitRepository.fruits.filter { 
                            it.name.contains(searchQuery, ignoreCase = true) 
                        }
                        if (suggestions.isNotEmpty()) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 60.dp)
                                    .zIndex(1f),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Column {
                                    suggestions.take(5).forEach { fruit ->
                                        Text(
                                            text = fruit.name,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    searchQuery = fruit.name
                                                    showSearchSuggestions = false
                                                    navController.navigate("details/${fruit.id}")
                                                }
                                                .padding(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Image and Text Banner
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp).clip(RoundedCornerShape(20.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(R.color.white))
                ) {
                    Box(modifier = Modifier.clip(RoundedCornerShape(20.dp)).fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.shop_now),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize().fillMaxWidth().clip(RoundedCornerShape(20.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier
                                .padding(start=20.dp,top=5.dp)
                                .align(Alignment.CenterStart)
                        ) {
                            Text("Fresh & Organic \nFruits", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                            Text("Delivered to your home", color = Color.White, fontWeight = FontWeight.Bold)
                            Button(
                                onClick = { navController.navigate("categories") },
                                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.white), contentColor = colorResource(R.color.black)),
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                Text("Shop Now", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Filters
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(FruitCategory.entries.toTypedArray()) { category ->
                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category },
                            label = { Text(category.name) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = colorResource(R.color.green),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Popular Fruits", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                
                // Grid of Fruits
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredFruits, key = { it.id }) { fruit ->
                        FruitCard(fruit) {
                            navController.navigate("details/${fruit.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.user_logo),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Color.LightGray)
        )
        Spacer(Modifier.height(12.dp))
        Text("Hello, ${FruitRepository.userName.value} \uD83D\uDC4B", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text("Welcome to Fruitify", color = Color.Gray)
    }
    Spacer(modifier=Modifier.fillMaxWidth().background(colorResource(R.color.black)).height(1.dp))
}

@Composable
fun FruitCard(fruit: Fruit, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
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
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(fruit.name, fontWeight = FontWeight.Bold)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("₹${fruit.price}/kg", color = colorResource(id = R.color.green))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, null, tint = Color(0xFFFFB800), modifier = Modifier.size(14.dp))
                    Text(fruit.rating.toString(), fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute = navController.currentDestination?.route
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, "Home") },
            label = { Text("Home") },
            selected = currentRoute == "home",
            onClick = { navController.navigate("home") { popUpTo("home") { inclusive = true } } }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Category, "Categories") },
            label = { Text("Categories") },
            selected = currentRoute == "categories",
            onClick = { navController.navigate("categories") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ShoppingCart, "Cart") },
            label = { Text("Cart") },
            selected = currentRoute == "cart",
            onClick = { navController.navigate("cart") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ListAlt, "Orders") },
            label = { Text("Orders") },
            selected = currentRoute == "orders",
            onClick = { navController.navigate("orders") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, "Profile") },
            label = { Text("Profile") },
            selected = currentRoute == "profile",
            onClick = { navController.navigate("profile") }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun preview6(){
    val navController = rememberNavController()

    HomeScreen(navController)
}