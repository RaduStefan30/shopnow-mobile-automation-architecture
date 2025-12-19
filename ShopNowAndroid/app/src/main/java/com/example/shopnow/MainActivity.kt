package com.example.shopnow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.shopnow.app.ShopNowApp
import com.example.shopnow.ui.theme.ShopNowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ShopNowTheme {
                ShopNowApp()
            }
        }
    }
}
