package com.ioline.tradebot.features.authorization.presentation

internal data class AuthorizationState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val passwordConfirmError: String? = null,
    val registrationMode: Boolean = false
) {
    val isFormValid: Boolean
        get() = emailError == null && passwordError == null &&
                email.isNotBlank() && password.isNotBlank()
}

internal sealed class AuthorizationEffect {
    // your code
}

internal sealed class AuthorizationCommand {
    data class LogIn(val email: String, val password: String) : AuthorizationCommand()
}

internal sealed class AuthorizationEvent {
    sealed class Internal : AuthorizationEvent() {
        // your code
    }

    sealed class Ui : AuthorizationEvent() {
        object System {
            object Init : Ui()
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