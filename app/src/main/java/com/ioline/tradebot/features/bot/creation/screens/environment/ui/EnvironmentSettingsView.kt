package com.ioline.tradebot.features.bot.creation.screens.environment.ui

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.data.models.OperationMode
import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsState
import java.util.Date
import java.util.Locale
import com.ioline.tradebot.features.bot.creation.screens.environment.presentation.EnvironmentSettingsEvent as Event

@Composable
internal fun EnvironmentSettingsView(
    state: EnvironmentSettingsState,
    onEvent: (Event) -> Unit
) {
    var token by remember { mutableStateOf("") }
    var tokenError by remember { mutableStateOf(false) }
    var isValidatingToken by remember { mutableStateOf(false) }
    var historicalRange by remember { mutableStateOf<String?>(null) }
    var showLargeRangeWarning by remember { mutableStateOf(false) }
    var startDate by remember { mutableStateOf(state.startDate) }
    var endDate by remember { mutableStateOf(state.endDate) }
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (state.type) {
                MarketEnvironment.HISTORICAL_DATA -> {
                    Text("Choose period")
                    DatePickerDialogComponent(
                        "Start date",
                        startDate
                    ) { onEvent(Event.Ui.Click.SelectStartDate(it)) }
                    DatePickerDialogComponent("End date", endDate) {
                        onEvent(Event.Ui.Click.SelectEndDate(it))
                    }
                    if (showLargeRangeWarning) {
                        Text(
                            text = "Long period could takes long time",
                            color = Color.Red
                        )
                    }
                }

                MarketEnvironment.SANDBOX -> {
                    Text("Enter the token for sandbox")
                    OutlinedTextField(
                        value = token,
                        onValueChange = { token = it; tokenError = false },
                        label = { Text("Token") },
                        isError = tokenError
                    )
                    if (tokenError) {
                        Text(
                            text = "Token is incorrect, please read the instruction",
                            color = Color.Red
                        )
                        TextButton(onClick = { onEvent(Event.Ui.Click.ShowInstruction) }) {
                            Text("Show instruction")
                        }
                    }
                    Button(
                        onClick = {
                            isValidatingToken = true
                            tokenError = false
                            onEvent(Event.Ui.Click.ValidateToken(token))
                        },
                        enabled = !isValidatingToken
                    ) {
                        if (isValidatingToken) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp))
                        } else {
                            Text("Check token")
                        }
                    }
                }

                MarketEnvironment.MARKET -> {
                    Text("Реальное окружение")
                    Text(
                        text = "Использование реального окружения связано с рисками. Убедитесь, что вы понимаете последствия.",
                        color = Color.Red
                    )
                    OutlinedTextField(
                        value = token,
                        onValueChange = { token = it; tokenError = false },
                        label = { Text("Токен") },
                        isError = tokenError
                    )
                    if (tokenError) {
                        Text(
                            text = "Токен неверный. Пожалуйста, ознакомьтесь с инструкцией.",
                            color = Color.Red
                        )
                        TextButton(onClick = { onEvent(Event.Ui.Click.ShowInstruction) }) {
                            Text("Посмотреть инструкцию")
                        }
                    }
                    Button(
                        onClick = {
                            isValidatingToken = true
                            tokenError = false
                            onEvent(Event.Ui.Click.ValidateToken(token))
                        },
                        enabled = !isValidatingToken
                    ) {
                        if (isValidatingToken) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp))
                        } else {
                            Text("Проверить токен")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onEvent(Event.Ui.Click.SaveBot)
                }) {
                Text("Save bot")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogComponent(
    label: String,
    selectedDate: Date?,
    onDateSelected: (Date) -> Unit
) {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    var showDialog by remember { mutableStateOf(false) }
    var displayDate by remember {
        mutableStateOf(selectedDate?.let { dateFormat.format(it) } ?: "")
    }

    Column {
        OutlinedTextField(
            value = displayDate,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier
                .clickable { showDialog = true }
                .fillMaxWidth()
        )

        if (showDialog) {
            DatePickerDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = selectedDate?.time ?: System.currentTimeMillis()
                )

                DatePicker(
                    state = datePickerState,
                    modifier = Modifier.padding(16.dp)
                )

                LaunchedEffect(datePickerState.selectedDateMillis) {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val newDate = Date(millis)
                        displayDate = dateFormat.format(newDate)
                        onDateSelected(newDate)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun EnvironmentSettingsMarketPreview() {
    EnvironmentSettingsView(
        state = EnvironmentSettingsState(
            type = MarketEnvironment.MARKET, bot = Bot(
                id = "nec",
                name = "Gay Keller",
                description = "instructior",
                strategy = null,
                isActive = false,
                instrumentsFIGI = listOf(),
                marketEnvironment = MarketEnvironment.MARKET,
                timeSettings = null,
                mode = OperationMode.MANUAL,
                result = null
            ), startDate = Date(2), endDate = Date(2), token = "varius"
        ),
        {}
    )
}

@Preview
@Composable
private fun EnvironmentSettingsSandboxPreview() {
    EnvironmentSettingsView(
        state = EnvironmentSettingsState(
            type = MarketEnvironment.SANDBOX, bot = Bot(
                id = "nec",
                name = "Gay Keller",
                description = "instructior",
                strategy = null,
                isActive = false,
                instrumentsFIGI = listOf(),
                marketEnvironment = MarketEnvironment.MARKET,
                timeSettings = null,
                mode = OperationMode.MANUAL,
                result = null
            ), startDate = Date(2), endDate = Date(2), token = "varius"
        ),
        {}
    )
}

@Preview
@Composable
private fun EnvironmentSettingsPreview() {
    EnvironmentSettingsView(
        state = EnvironmentSettingsState(
            type = MarketEnvironment.HISTORICAL_DATA, bot = Bot(
                id = "nec",
                name = "Gay Keller",
                description = "instructior",
                strategy = null,
                isActive = false,
                instrumentsFIGI = listOf(),
                marketEnvironment = MarketEnvironment.MARKET,
                timeSettings = null,
                mode = OperationMode.MANUAL,
                result = null
            ), startDate = Date(2), endDate = Date(2), token = "varius"
        ),
        {}
    )
}