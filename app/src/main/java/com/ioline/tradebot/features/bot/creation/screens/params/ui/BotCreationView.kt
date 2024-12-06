package com.ioline.tradebot.features.bot.creation.screens.params.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.R
import com.ioline.tradebot.data.models.Instrument
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.data.models.OperationMode
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationEvent
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BotCreationView(state: BotCreationState, onEvent: (BotCreationEvent) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.bot_creation_title)) },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "back",
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BotNameField(state.name, state.errorBotNameValidation, onEvent)
            BotDescriptionField(state.description, onEvent)

            DropdownField(
                label = stringResource(R.string.market_drop_down_label),
                options = MarketEnvironment.entries.map { it.value },
                selectedOption = state.marketEnvironment.value
            ) { value ->
                onEvent(BotCreationEvent.Ui.ChangeMarket(value))
            }

            if (state.marketEnvironment != MarketEnvironment.HISTORICAL_DATA) {
                DropdownField(
                    label = stringResource(R.string.mode_drop_down_label),
                    options = OperationMode.entries.map { it.value },
                    selectedOption = state.mode.value
                ) { value ->
                    onEvent(BotCreationEvent.Ui.ChangeMode(value))
                }
            }

            SearchTicketsField(state, onEvent)
            SelectedTickers(
                tags = state.selectedInstruments.groupBy { it.name }.entries.map { it.key to it.value.size },
                onRemoveTag = { onEvent(BotCreationEvent.Ui.Click.RemoveInstrument(it)) }
            )

            Text("Total amount: ${state.selectedInstruments.map { it.price }.sum()}")

            Spacer(Modifier.weight(1f))

            Button(
                onClick = { onEvent(BotCreationEvent.Ui.Click.Next) },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors()
            ) {
                Text(stringResource(R.string.button_next))
            }
        }
    }

}

@Composable
internal fun BotNameField(name: String, isError: Boolean, onEvent: (BotCreationEvent) -> Unit) {
    OutlinedTextField(
        value = name,
        onValueChange = {
            onEvent(BotCreationEvent.Ui.ChangeBotName(it))
        },
        label = { Text(stringResource(R.string.bots_name_label)) },
        modifier = Modifier.fillMaxWidth(),
        isError = isError
    )
}

@Composable
internal fun BotDescriptionField(description: String, onEvent: (BotCreationEvent) -> Unit) {
    OutlinedTextField(
        value = description,
        onValueChange = {
            onEvent(BotCreationEvent.Ui.ChangeBotDescription(it))
        },
        label = { Text(stringResource(R.string.bot_description_label)) },
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onSelect: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded }
    ) {
        TextField(
            value = selectedOption,
            onValueChange = { onSelect(selectedOption) },
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelect(option)
                        isExpanded = false
                    }
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
            marketEnvironment = MarketEnvironment.HISTORICAL_DATA,
            mode = OperationMode.MANUAL,
            searchInstruments = listOf(
                Instrument(
                    classCode = "TSLA",
                    figi = "BBG000N9MNX3",
                    first1dayCandleDate = "2010-06-29",
                    first1minCandleDate = "2015-06-29",
                    forIisFlag = true,
                    instrumentType = "Equity",
                    name = "Tesla Inc.",
                    ticker = "TSLA",
                    uid = "uid3",
                    price = 800.0
                ),
                Instrument(
                    classCode = "MSFT",
                    figi = "BBG000BPH459",
                    first1dayCandleDate = "1986-03-13",
                    first1minCandleDate = "1990-01-01",
                    forIisFlag = false,
                    instrumentType = "Equity",
                    name = "Microsoft Corp.",
                    ticker = "MSFT",
                    uid = "uid4",
                    price = 299.0
                ),
                Instrument(
                    classCode = "AMZN",
                    figi = "BBG000BVPV84",
                    first1dayCandleDate = "1997-05-15",
                    first1minCandleDate = "2000-05-15",
                    forIisFlag = true,
                    instrumentType = "Equity",
                    name = "Amazon.com Inc.",
                    ticker = "AMZN",
                    uid = "uid5",
                    price = 3300.0
                )
            ),
            selectedInstruments = listOf(
                Instrument(
                    classCode = "TSLA",
                    figi = "BBG000N9MNX3",
                    first1dayCandleDate = "2010-06-29",
                    first1minCandleDate = "2015-06-29",
                    forIisFlag = true,
                    instrumentType = "Equity",
                    name = "Tesla Inc.",
                    ticker = "TSLA",
                    uid = "uid3",
                    price = 800.0
                ),
                Instrument(
                    classCode = "MSFT",
                    figi = "BBG000BPH459",
                    first1dayCandleDate = "1986-03-13",
                    first1minCandleDate = "1990-01-01",
                    forIisFlag = false,
                    instrumentType = "Equity",
                    name = "Microsoft Corp.",
                    ticker = "MSFT",
                    uid = "uid4",
                    price = 299.0
                ),
                Instrument(
                    classCode = "AMZN",
                    figi = "BBG000BVPV84",
                    first1dayCandleDate = "1997-05-15",
                    first1minCandleDate = "2000-05-15",
                    forIisFlag = true,
                    instrumentType = "Equity",
                    name = "Amazon.com Inc.",
                    ticker = "AMZN",
                    uid = "uid5",
                    price = 3300.0
                )
            ),
            searchInstrumentsLoading = false,
            searchInstrumentError = false
        )
    ) { }
}