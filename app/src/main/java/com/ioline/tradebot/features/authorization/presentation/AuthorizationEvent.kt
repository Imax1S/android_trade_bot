package com.ioline.tradebot.features.authorization.presentation

internal sealed class AuthorizationEvent {
    sealed class Internal : AuthorizationEvent() {
        data class CreateAccount(val email: String) : Internal()
    }

    sealed class Ui : AuthorizationEvent() {
        object System {
            data object Init : Ui()
        }

        object Click {
            data object Login : Ui()
            data object GoToRegistration : Ui()
            data object GoToLogin : Ui()
        }

        object Action {
            data class EmailChanged(val email: String) : Ui()
            data class PasswordChanged(val password: String) : Ui()
            data class PasswordConfirmChanged(val password: String) : Ui()
        }
    }
}