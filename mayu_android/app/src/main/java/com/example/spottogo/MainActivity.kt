package com.example.spottogo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spottogo.data.Restaurant
import com.example.spottogo.ui.detail.RestaurantDetailScreen
import com.example.spottogo.ui.login.LoginScreen
import com.example.spottogo.ui.map.MapScreen
import com.example.spottogo.ui.theme.SpotToGoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpotToGoTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var selectedRestaurant by remember { mutableStateOf<Restaurant?>(null) }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(onLoginSuccess = {
                navController.navigate("map") {
                    popUpTo("login") { inclusive = true }
                }
            })
        }
        composable("map") {
            MapScreen(onRestaurantClick = { restaurant ->
                selectedRestaurant = restaurant
                navController.navigate("detail")
            })
        }
        composable("detail") {
            selectedRestaurant?.let { restaurant ->
                RestaurantDetailScreen(
                    restaurant = restaurant,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
