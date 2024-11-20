package com.ioline.tradebot.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavItem(val route: String) {
    @Serializable
    data object HomeScreen : NavItem("home_screen")

    @Serializable
    data object BotCreation : NavItem("bot_creation")
    data object StrategySettings : NavItem("strategy_settings")

    @Serializable
    data class StrategySelection(val botId: String) : NavItem("strategy_selection")
    data object EnvironmentSettings : NavItem("environment_settings")
    data object BotReview : NavItem("bot_review")
    data object Settings : NavItem("settings")
}