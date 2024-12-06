package com.ioline.tradebot.features.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.R
import com.ioline.tradebot.features.settings.presentation.SettingsEvent as Event
import com.ioline.tradebot.features.settings.presentation.SettingsState as State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsView(state: State, onEvent: (Event) -> Unit) {
    var token by remember { mutableStateOf("") }
    var tokenError by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { "" }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Тема
            Text(
                text = "Appearance",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            ThemeToggle(state.isDarkTheme, onEvent)

            Spacer(modifier = Modifier.height(16.dp))

            // Уведомления
            Text(
                text = "Notifications",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            NotificationToggle()

            Spacer(modifier = Modifier.height(16.dp))

            // Авторизация
            Text(
                text = "Account",
                fontWeight = FontWeight.Bold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (state.user == null) {
                    Text(
                        text = "You are not logged in",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.bot_card_background)
                    )
                } else {
                    Text(
                        text = "Logged in as ${state.user.name}",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.bot_card_background)
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(
                        Icons.Default.AccountCircle,
                        "Avatar icon",
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Token",
                fontWeight = FontWeight.Bold
            )
            if (state.user == null) {
                Text(
                    text = "You should log in to enter the token",
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.bot_card_background)
                )
            } else {
                OutlinedTextField(
                    value = state.user.token,
                    onValueChange = {
                        onEvent(Event.Ui.EnterToken(it))
                        tokenError = false
                    },
                    label = { Text("token") },
                    isError = state.tokenError
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            AuthButton(state, onEvent)
        }
    }
}

@Composable
internal fun ThemeToggle(isDarkTheme: Boolean, onEvent: (Event) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Dark Theme",
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = isDarkTheme,
            onCheckedChange = { onEvent(Event.Ui.ChangeUiTheme) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF6A4FA0)
            )
        )
    }
}

@Composable
fun NotificationToggle() {
    var enableNotification by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Enable Notifications",
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = enableNotification,
            onCheckedChange = { enableNotification = !enableNotification },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF6A4FA0)
            )
        )
    }
}

@Composable
internal fun AuthButton(state: State, onEvent: (Event) -> Unit) {
    Button(
        onClick = {
            if (state.user == null) {
                onEvent(Event.Ui.LogIn)
            } else {
                onEvent(Event.Ui.LogOut)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(contentColor = Color(0xFF6A4FA0)),
        shape = RoundedCornerShape(24.dp)
    ) {
        if (state.user == null) {
            Text("Log In", color = Color.White)
        } else {
            Text("Log Out", color = Color.White)
        }
    }
}

@Preview
@Composable
private fun SettingsPreview() {
    SettingsView(state = State()) {}
}
