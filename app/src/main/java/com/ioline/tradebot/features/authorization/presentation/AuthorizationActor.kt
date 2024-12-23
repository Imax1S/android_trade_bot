package com.ioline.tradebot.features.authorization.presentation

import com.ioline.tradebot.features.authorization.presentation.AuthorizationEvent.Internal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject

internal class AuthorizationActor @Inject constructor(
    // your dependencies
) : Actor<AuthorizationCommand, Internal> {

    override fun execute(
        command: AuthorizationCommand
    ): Flow<Internal> = flow {
        when (command) {
            is AuthorizationCommand.LogIn -> {}
            is AuthorizationCommand.CreateAccount -> {
                emit(Internal.CreateAccount(command.email))
            }
        }
    }
}