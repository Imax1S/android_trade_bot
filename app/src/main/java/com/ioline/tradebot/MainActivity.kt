package com.ioline.tradebot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ioline.tradebot.common_ui.theme.TradeBotTheme
import com.ioline.tradebot.data.repository.bot.BotRepository
import com.ioline.tradebot.data.repository.instrument.InstrumentRepository
import com.ioline.tradebot.features.bot.creation.screens.environment.EnvironmentSettingsScreen
import com.ioline.tradebot.features.bot.creation.screens.params.BotCreationScreen
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.StrategySelectionScreen
import com.ioline.tradebot.features.bot.creation.screens.strategy.setup.StrategySetupScreen
import com.ioline.tradebot.features.bot.overview.BotReviewScreen
import com.ioline.tradebot.features.home.HomeScreen
import com.ioline.tradebot.features.settings.SettingsScreen
import com.ioline.tradebot.navigation.NavItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var botRepository: BotRepository

    @Inject
    lateinit var instrumentRepository: InstrumentRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            TradeBotTheme {
                Surface {
                    Scaffold { padding ->
                        TradeBotNavHost(navController, padding)
                    }
                }
            }
        }
    }

    @Composable
    private fun TradeBotNavHost(navController: NavHostController, padding: PaddingValues) {
        NavHost(
            navController = navController,
            startDestination = NavItem.HomeScreen
        ) {
            composable<NavItem.HomeScreen> {
                HomeScreen(
                    botRepository
                ) { screenName ->
                    navController.navigate(screenName)
                }
            }
            composable<NavItem.BotCreation> {
                BotCreationScreen(
                    instrumentRepository = instrumentRepository
                ) { screenName ->
                    navController.navigate(screenName)
                }
            }
            composable<NavItem.StrategySelection> { argument ->
                val botId = argument.toRoute<NavItem.StrategySelection>().botId
                StrategySelectionScreen(botId) { screenName ->
                    navController.navigate(screenName)
                }
            }
            composable(NavItem.StrategySettings.route) {
                StrategySetupScreen { screenName ->
                    navController.navigate(screenName) {
                        popUpTo(screenName) { inclusive = true }
                    }
                }
            }
            composable<NavItem.BotReview> { argument ->
                val botId = argument.toRoute<NavItem.StrategySelection>().botId

                BotReviewScreen(botId, botRepository) { screenName ->
                    when (screenName) {
                        NavItem.HomeScreen -> {
                            navController.navigate(screenName.route) {
                                popUpTo(screenName.route) { inclusive = true }
                            }
                        }
                        else -> {
                            navController.navigate(screenName.route)
                        }
                    }
                }
            }
            composable(NavItem.Settings.route) {
                SettingsScreen { screenName ->
                    navController.navigate(screenName)
                }
            }
            composable(NavItem.EnvironmentSettings.route) {
                EnvironmentSettingsScreen { screenName ->
                    navController.navigate(screenName.route) {
                        popUpTo(screenName.route) { inclusive = true }
                    }
                }
            }
        }
    }
}