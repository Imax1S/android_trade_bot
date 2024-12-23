package com.ioline.tradebot.features.authorization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.ioline.tradebot.features.authorization.presentation.AuthorizationActor
import com.ioline.tradebot.features.authorization.presentation.AuthorizationEffect
import com.ioline.tradebot.features.authorization.presentation.AuthorizationReducer
import com.ioline.tradebot.features.authorization.presentation.AuthorizationState
import com.ioline.tradebot.features.authorization.ui.AuthorizationView
import vivid.money.elmslie.coroutines.ElmStoreCompat
import vivid.money.elmslie.coroutines.effects
import vivid.money.elmslie.coroutines.states

@Composable
fun AuthorizationScreen(navigate: (String) -> Boolean) {
    val initialState = AuthorizationState("")

    val store = remember {
        ElmStoreCompat(
            initialState,
            AuthorizationReducer,
            AuthorizationActor()
        )
    }

    val state by store.states.collectAsState(
        initialState
    )

    val effect by store.effects.collectAsState(null)

    effect?.let {
        LaunchedEffect(it) {
            when (it) {
                AuthorizationEffect.Close -> navigate("")
            }
        }
    }

    AuthorizationView(state) {
        store.accept(it)
    }
}