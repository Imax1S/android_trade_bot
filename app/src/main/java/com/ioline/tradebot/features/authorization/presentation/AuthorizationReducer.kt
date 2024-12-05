package com.ioline.tradebot.features.authorization.presentation

import android.util.Patterns
import com.ioline.tradebot.features.authorization.presentation.AuthorizationEvent.Internal
import com.ioline.tradebot.features.authorization.presentation.AuthorizationEvent.Ui
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object AuthorizationReducer :
    ScreenDslReducer<AuthorizationEvent, Ui, Internal, AuthorizationState,
            AuthorizationEffect, AuthorizationCommand>(Ui::class, Internal::class) {

    override fun Result.internal(event: Internal) = when (event) {

        else -> {}
    }

    override fun Result.ui(event: Ui) = when (event) {
        is Ui.Action.EmailChanged -> validateEmail(event.email) { error ->
            state {
                copy(
                    email = event.email,
                    emailError = error
                )
            }
        }
        Ui.System.Init -> TODO()
        Ui.Click.Login -> commands {
            +AuthorizationCommand.LogIn(state.email, state.password)
        }
        is Ui.Action.PasswordChanged -> validatePassword(event.password) { error ->
            state {
                copy(
                    password = event.password,
                    passwordError = error
                )
            }
        }
        Ui.Click.GoToRegistration -> state {
            copy(registrationMode = true)
        }
        is Ui.Action.PasswordConfirmChanged -> state {
            copy(confirmPassword = event.password)
        }
        Ui.Click.GoToLogin -> state {
            copy(registrationMode = false)
        }
    }

    private fun validateEmail(email: String, changeState: (String?) -> Unit) {
        val error = validateEmailFormat(email)
        changeState(error)
    }

    private fun validatePassword(password: String, changeState: (String?) -> Unit) {
        val error = validatePasswordFormat(password)
        changeState(error)
    }

    private fun validateEmailFormat(email: String): String? {
        return if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            null
        } else {
            "Неверный формат email"
        }
    }

    private fun validatePasswordFormat(password: String): String? {
        return if (password.length >= 6) {
            null
        } else {
            "Пароль должен содержать минимум 6 символов"
        }
    }
}