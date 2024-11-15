package com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.data.models.strategy.Strategy
import com.ioline.tradebot.data.models.strategy.StrategyType
import com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation.StrategySetupEvent
import com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation.StrategySetupState

@Composable
internal fun StrategySetupView(state: StrategySetupState, onEvent: (StrategySetupEvent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(onClick = { /* Handle back navigation */ }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Setup strategy",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        StrategyHeader()
        Spacer(modifier = Modifier.height(24.dp))
        ParamField(label = "First param", value = "12")
        Spacer(modifier = Modifier.height(16.dp))
        ParamField(label = "Second param", value = "45")

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onEvent(StrategySetupEvent.Ui.Click.Create) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text("Launch")
        }
    }
}

@Composable
fun StrategyHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Hamster strategy",
            )
            Text(
                text = "That is a simple strategy which use a simple principle",
            )
        }
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Info",
            tint = Color(0xFF6A4FA0)
        )
    }
}

@Composable
fun ParamField(label: String, value: String) {
    OutlinedTextField(
        value = value,
        onValueChange = { /* Handle value change */ },
        label = { Text(label) },
        leadingIcon = {
            Icon(Icons.Default.Favorite, contentDescription = "Favorite icon")
        },
        trailingIcon = {
            Icon(Icons.Default.Favorite, contentDescription = "Favorite icon")
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun StrategySetupPreview() {
    StrategySetupView(
        state = StrategySetupState(
            strategy = Strategy(
                type = StrategyType.EMA,
                description = "",
                param1 = "interdum",
                param2 = "graeco"
            )
        ),
        onEvent = {}
    )
}
