package com.ioline.tradebot.features.bot.creation.screens.environment.ui

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
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
    var showLargeRangeWarning by remember { mutableStateOf(false) }

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
                    Text("Choose period", style = MaterialTheme.typography.headlineLarge)
                    Text(
                        "Select range of date to test your bot on data.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    DatePickerFieldToModal("From date")
                    DatePickerFieldToModal("To date")
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
fun DatePickerDocked(title: String) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text(title) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false
                    )
                }
            }
        }
    }
}

@Composable
fun DatePickerFieldToModal(title: String) {
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var showModal by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = selectedDate?.let { convertMillisToDate(it) } ?: "",
        onValueChange = { },
        label = { Text(title) },
        placeholder = { Text("MM/DD/YYYY") },
        trailingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = "Select date")
        },
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(selectedDate) {
                awaitEachGesture {
                    // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                    // in the Initial pass to observe events before the text field consumes them
                    // in the Main pass.
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showModal = true
                    }
                }
            }
    )

    if (showModal) {
        DatePickerModal(
            onDateSelected = { selectedDate = it },
            onDismiss = { showModal = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
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