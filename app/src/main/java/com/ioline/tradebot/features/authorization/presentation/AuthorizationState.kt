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