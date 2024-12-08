package com.ioline.tradebot.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavItem(val route: String) {
    @Serializable
    data object HomeScreen : NavItem("home_screen")

    @Serializable
    data object BotCreation : NavItem("bot_creation")

    @Serializable
    data object StrategySettings : NavItem("strategy_settings")

    @Serializable
    data class StrategySelection(val botId: String) : NavItem("strategy_selection")

    @Serializable
    data class EnvironmentSettings(val botId: String) : NavItem("environment_settings")

    @Serializable
    data class BotReview(val botId: String) : NavItem("bot_review")

    @Serializable
    data object Authorization : NavItem("authorization")

    @Serializable
    data object Settings : NavItem("settings")
}