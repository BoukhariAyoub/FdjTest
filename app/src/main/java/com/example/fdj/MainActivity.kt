package com.example.fdj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.fdj.ui.screens.homepage.HomePageScreen
import com.example.fdj.ui.theme.FDJTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FDJTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomePageScreen()
                }
            }
        }
    }
}