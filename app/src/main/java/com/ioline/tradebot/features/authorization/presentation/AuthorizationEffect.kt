package com.ioline.tradebot.features.authorization.presentation

internal sealed class AuthorizationEffect {
    data object Close : AuthorizationEffect()
}