package com.example.unscramble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unscramble.ui.AddWordScreen
import com.example.unscramble.ui.GameScreen
import com.example.unscramble.ui.theme.UnscrambleTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            UnscrambleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    UnscrambleApp()
                }
            }
        }
    }
}

@Composable
fun UnscrambleApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "game"
    ) {

        composable("game") {
            GameScreen(
                onNavigateToAddWord = {
                    navController.navigate("add_word")
                }
            )
        }

        composable("add_word") {
            AddWordScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}