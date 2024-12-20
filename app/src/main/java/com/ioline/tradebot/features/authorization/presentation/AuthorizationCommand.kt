package com.ioline.tradebot.features.authorization.presentation

internal sealed class AuthorizationCommand {
    data class LogIn(val email: String, val password: String) : AuthorizationCommand()
    data class CreateAccount(val email: String, val password: String) : AuthorizationCommand()
}