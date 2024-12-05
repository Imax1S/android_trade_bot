package com.ioline.tradebot.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ioline.tradebot.data.repository.bot.BotRepository
import com.ioline.tradebot.data.repository.instrument.InstrumentRepository
import com.ioline.tradebot.features.authorization.AuthorizationScreen
import com.ioline.tradebot.features.bot.creation.screens.environment.EnvironmentSettingsScreen
import com.ioline.tradebot.features.bot.creation.screens.params.BotCreationScreen
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.StrategySelectionScreen
import com.ioline.tradebot.features.bot.creation.screens.strategy.setup.StrategySetupScreen
import com.ioline.tradebot.features.bot.overview.BotReviewScreen
import com.ioline.tradebot.features.home.HomeScreen
import com.ioline.tradebot.features.settings.SettingsScreen


@Composable
fun TradeBotNavHost(
    navController: NavHostController,
    padding: PaddingValues,
    botRepository: BotRepository,
    instrumentRepository: InstrumentRepository
) {
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
                instrumentRepository = instrumentRepository,
                botRepository = botRepository
            ) { screenName ->
                navController.navigate(screenName)
            }
        }
        composable<NavItem.StrategySelection> { argument ->
            val botId = argument.toRoute<NavItem.StrategySelection>().botId
            StrategySelectionScreen(
                botRepository = botRepository,
                botId
            ) { screenName ->
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
        composable<NavItem.Settings> {
            SettingsScreen { screenName ->
                navController.navigate(screenName)
            }
        }
        composable<NavItem.EnvironmentSettings> { argument ->
            val botId = argument.toRoute<NavItem.StrategySelection>().botId

            EnvironmentSettingsScreen(botId, botRepository) { screenName ->
                navController.navigate(screenName) {
                    popUpTo(screenName) { inclusive = true }
                }
            }
        }
        composable<NavItem.Authorization> {
            AuthorizationScreen()
        }
    }
}