package com.example.imageviewapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.imageviewapp.ui.screens.DetailScreen
import com.example.imageviewapp.ui.screens.FavoritesScreen
import com.example.imageviewapp.ui.screens.HomeScreen
import com.example.imageviewapp.ui.theme.ImageViewAppTheme
import com.example.imageviewapp.ui.theme.UnsplashGalleryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnsplashGalleryTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            val currentDestination = navController.currentBackStackEntryAsState().value?.destination

                            BottomNavItem.entries.forEach { item ->
                                NavigationBarItem(
                                    icon = { Icon(item.icon, contentDescription = item.title) },
                                    label = { Text(item.title) },
                                    selected = currentDestination?.route == item.route,
                                    onClick = {
                                        if (currentDestination?.route != item.route) {
                                            navController.navigate(item.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        } else {
                                            navController.popBackStack(BottomNavItem.Home.route, inclusive = false)
                                        }
                                    }
                                )

                            }
                        }
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = BottomNavItem.Home.route,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable(BottomNavItem.Home.route) {
                            HomeScreen(
                                onNavigateToDetail = { photoId ->
                                    navController.navigate("detail/$photoId")
                                }
                            )
                        }

                        composable(BottomNavItem.Favorites.route) {
                            FavoritesScreen(
                                onNavigateToDetail = { photoId ->
                                    navController.navigate("detail/$photoId")
                                }
                            )
                        }

                        composable(
                            route = "detail/{photoId}",
                            arguments = listOf(
                                navArgument("photoId") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val photoId = backStackEntry.arguments?.getString("photoId") ?: ""
                            DetailScreen(
                                photoId = photoId,
                                onBackPressed = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}

enum class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    Home("home", "Home", Icons.Default.Home),
    Favorites("favorites", "Favorites", Icons.Default.Favorite)
}
