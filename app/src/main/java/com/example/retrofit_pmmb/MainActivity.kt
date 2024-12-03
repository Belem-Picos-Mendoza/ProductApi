package com.example.retrofit_pmmb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.retrofit_pmmb.ui.theme.ProductTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    ProductsApp()
                }
            }
        }
    }
}