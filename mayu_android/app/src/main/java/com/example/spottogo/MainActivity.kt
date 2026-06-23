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
import com.example.spottogo.data.AuthRepository
import com.example.spottogo.data.Restaurant
import com.example.spottogo.ui.contact.ContactUsScreen
import com.example.spottogo.ui.directions.DirectionsScreen
import com.example.spottogo.ui.detail.RestaurantDetailScreen
import com.example.spottogo.ui.home.HomeScreen
import com.example.spottogo.ui.login.LoginScreen
import com.example.spottogo.ui.map.MapScreen
import com.example.spottogo.ui.privacy.PrivacyPolicyScreen
import com.example.spottogo.ui.tiktok.TikTokLinkScreen
import com.example.spottogo.ui.register.RegisterScreen
import com.example.spottogo.ui.splash.SplashScreen
import com.example.spottogo.ui.theme.SpotToGoTheme
import com.example.spottogo.ui.video.VideoScreen

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

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(
                onSplashComplete = {
                    // Skip login entirely if user already has an active Firebase session
                    val destination = if (AuthRepository.isLoggedIn) "map" else "home"
                    navController.navigate(destination) {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            HomeScreen(
                onExploreNearby = { navController.navigate("map") },
                onNavigateToLogin = { navController.navigate("login") },
                onNavigateToMap = { navController.navigate("map") },
                onNavigateToContact = { navController.navigate("contact") },
                onNavigateToPrivacy = { navController.navigate("privacy") }
            )
        }

        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("map") {
                        popUpTo("home") { inclusive = false }
                    }
                },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("map") {
                        popUpTo("home") { inclusive = false }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
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
                    onBack = { navController.popBackStack() },
                    onWatchVideo = { navController.navigate("video") },
                    onGetDirections = { navController.navigate("directions") }
                )
            }
        }

        composable("directions") {
            selectedRestaurant?.let { restaurant ->
                DirectionsScreen(
                    restaurant = restaurant,
                    onBack = { navController.popBackStack() }
                )
            }
        }

        composable("video") {
            selectedRestaurant?.let { restaurant ->
                VideoScreen(
                    restaurant = restaurant,
                    onBack = { navController.popBackStack() },
                    onOpenTikTok = { navController.navigate("tiktok") }
                )
            }
        }

        composable("tiktok") {
            selectedRestaurant?.let { restaurant ->
                TikTokLinkScreen(
                    restaurant = restaurant,
                    onBack = { navController.popBackStack() }
                )
            }
        }

        composable("contact") {
            ContactUsScreen(onBack = { navController.popBackStack() })
        }

        composable("privacy") {
            PrivacyPolicyScreen(onBack = { navController.popBackStack() })
        }
    }
}
