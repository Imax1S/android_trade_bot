package com.ioline.tradebot.navigation

sealed class NavItem(val route: String) {
    data object HomeScreen : NavItem("home_screen")
    data object BotCreation : NavItem("bot_creation")
    data object StrategySelection : NavItem("strategy_selection")
    data object StrategySettings : NavItem("strategy_settings")
    data object EnvironmentSettings : NavItem("environment_settings")
    data object BotReview : NavItem("bot_review")
    data object Settings : NavItem("settings")
}