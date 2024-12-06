package com.ioline.tradebot.features.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.ioline.tradebot.data.models.User
import com.ioline.tradebot.features.settings.presentation.SettingsActor
import com.ioline.tradebot.features.settings.presentation.SettingsEffect
import com.ioline.tradebot.features.settings.presentation.SettingsReducer
import com.ioline.tradebot.features.settings.presentation.SettingsState
import com.ioline.tradebot.features.settings.ui.SettingsView
import com.ioline.tradebot.navigation.NavItem
import vivid.money.elmslie.coroutines.ElmStoreCompat
import vivid.money.elmslie.coroutines.effects
import vivid.money.elmslie.coroutines.states

@Composable
fun SettingsScreen(changeTheme: () -> Unit, darkTheme: Boolean, navigate: (NavItem) -> Unit) {
    val initialState = SettingsState(
        user = User(id = "vel", name = "New User", token = ""),
        isDarkTheme = darkTheme
    )
    val store = remember {
        ElmStoreCompat(
            initialState,
            SettingsReducer,
            SettingsActor()
        )
    }

    val state by store.states.collectAsState(
        initialState
    )

    val effect by store.effects.collectAsState(null)

    effect?.let {
        LaunchedEffect(it.hashCode()) {
            when (it) {
                SettingsEffect.Close -> TODO()
                SettingsEffect.NavigateToAuthorizationInScreen -> navigate(NavItem.Authorization)
                SettingsEffect.ChangeTheme -> {
                    changeTheme()
                }
            }
        }
    }

    SettingsView(state) {
        store.accept(it)
    }
}
