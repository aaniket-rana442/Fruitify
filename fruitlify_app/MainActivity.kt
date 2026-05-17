package com.example.fruitlify_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.fruitlify_app.ui.theme.theme.Fruitlify_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize the repository with context to enable SharedPreferences
        FruitRepository.init(this)
        
        setContent {
            Fruitlify_AppTheme {
                AppNavigation()
            }
        }
    }
}
