package com.ioline.tradebot.features.bot.creation.screens.params.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.features.bot.creation.screens.params.ui.common.UserInput

@Composable
fun BotCreationScreen() {

    Column(
        Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        UserInput("Bot's name", "Enter bot's name")
//        DropdownMenu(expanded = , onDismissRequest = { /*TODO*/ }) {
//
//        }
    }
}

@Preview
@Composable
fun BotCreationScreenPreview() {
    BotCreationScreen()
}