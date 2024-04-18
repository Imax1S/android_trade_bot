package com.ioline.tradebot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ioline.tradebot.features.home.presentation.homescreen.HomeState
import com.ioline.tradebot.features.home.presentation.homescreen.TrainEffect
import com.ioline.tradebot.features.home.presentation.homescreen.TrainViewModel
import com.ioline.tradebot.features.home.ui.HomeScreen
import com.ioline.tradebot.features.home.ui.TrainScreen
import com.ioline.tradebot.ui.navigation.BottomNavItem
import com.ioline.tradebot.ui.theme.TradeBotTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val trainViewModel: TrainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TradeBotTheme {
                Surface {
                    val navController = rememberNavController()

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = { BottomNavigationBar(navController) }
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = BottomNavItem.Home.route
                        ) {
                            composable(BottomNavItem.Home.route) { HomeScreen(HomeState()) {} }
                            composable(BottomNavItem.Profile.route) {
                                TrainScreen(viewModel = trainViewModel, effect = { effect ->
                                    when (effect) {
                                        TrainEffect.OpenDetailsAction -> {
                                            navController.navigate(BottomNavItem.Home.route) {
                                                popUpTo(navController.graph.startDestinationId)
                                                launchSingleTop = true
                                            }
                                        }
                                    }
                                }
                                )
                            }
                        }
                        it
                    }
                }
            }
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        NavigationBar {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            listOf(
                BottomNavItem.Home,
                BottomNavItem.Dashboard,
                BottomNavItem.Profile
            ).forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(item.icon, contentDescription = null) },
                    label = { Text(item.label) }
                )
            }
        }
    }
}