package com.ioline.tradebot.features.bot.creation.screens.params.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.data.models.OperationMode
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationEvent
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationState

@Composable
internal fun BotCreationView(state: BotCreationState, onEvent: (BotCreationEvent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(onClick = { BotCreationEvent.Ui.Click.Close }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Create bot",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        BotNameField()
        DropdownField(label = "Market", items = listOf("History", "Science", "Art"))
        DropdownField(label = "Mode", items = listOf("Auto", "Manual"))
        DropdownField(label = "Label", items = listOf("App", "Game", "Service"))

        Spacer(modifier = Modifier.height(16.dp))

        SelectedTags(
            tags = listOf("Yandex", "Tinkoff", "Google", "Netflix"),
            onRemoveTag = { /* Handle tag removal */ }
        )

        Spacer(Modifier.weight(1f))

        Button(
            onClick = { onEvent(BotCreationEvent.Ui.Click.Next) },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors()
        ) {
            Text("Next")
        }
    }
}

@Composable
fun BotNameField() {
    OutlinedTextField(
        value = "Mega bot",
        onValueChange = { /* Handle name change */ },
        label = { Text("Bot's name") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun DropdownField(label: String, items: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(items.first()) }

    Column {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = { },
            label = { Text(label) },
            readOnly = true,
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon")
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { option ->
                DropdownMenuItem(text = { Text(option) }, onClick = {
                    selectedOption = option
                    expanded = false
                })
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectedTags(tags: List<String>, onRemoveTag: (String) -> Unit) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
//        mainAxisSpacing = 8.dp,
//        crossAxisSpacing = 8.dp
    ) {
        tags.forEach { tag ->
            Chip(
                text = tag,
                onRemove = { onRemoveTag(tag) }
            )
        }
    }
}

@Composable
fun Chip(text: String, onRemove: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(text = text)
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(onClick = onRemove, modifier = Modifier.size(16.dp)) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove tag",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun BotCreationViewPreview() {
    BotCreationView(
        BotCreationState(
            name = "Amos Hood",
            description = "repudiandae",
            marketEnvironment = MarketEnvironment.MARKET,
            mode = OperationMode.MANUAL,
            searchInstruments = listOf(),
            selectedInstruments = listOf(),
            searchInstrumentsLoading = false,
            searchInstrumentError = false
        )
    ) { }
}