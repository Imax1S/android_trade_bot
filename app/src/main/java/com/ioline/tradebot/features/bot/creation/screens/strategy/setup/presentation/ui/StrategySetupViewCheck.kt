package com.ioline.tradebot.features.bot.creation.screens.strategy.setup.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ioline.tradebot.R
import com.ioline.tradebot.data.models.strategy.StrategyType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StrategyParametersScreen() {
    var selectedStrategy by remember { mutableStateOf(StrategyType.EMA) }
    val strategies = StrategyType.entries
    var period by remember { mutableFloatStateOf(14f) }
    var overboughtThreshold by remember { mutableIntStateOf(70) }
    var oversoldThreshold by remember { mutableIntStateOf(30) }
    var crossoverEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.set_up_strategy_title)) },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(stringResource(R.string.select_strategy_title))
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                strategies.forEach { strategy ->
                    Button(
                        onClick = { selectedStrategy = strategy },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedStrategy == strategy) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.secondary
                            }
                        )
                    ) {
                        Text(strategy.name)
                    }
                }
            }

            Text("Description")
            when (selectedStrategy) {
                StrategyType.EMA -> Text("EMA is ...")
                StrategyType.RSI -> Text("RSI is ...")
                StrategyType.CUSTOM -> Text("Custom is ...")
                StrategyType.MA -> Text("Ma is ...")
            }

            // Parameter Settings
            when (selectedStrategy) {
                StrategyType.EMA, StrategyType.MA -> {
                    // Period Slider
                    Text("Period: ${period.toInt()}", fontSize = 18.sp)
                    Slider(
                        value = period,
                        onValueChange = { period = it },
                        valueRange = 1f..100f
                    )

                    // Crossover toggle
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text("Enable Crossover Signals")
                        Switch(
                            checked = crossoverEnabled,
                            onCheckedChange = { crossoverEnabled = it }
                        )
                    }
                }
                StrategyType.RSI -> {
                    // RSI-specific settings
                    Text("RSI Period: ${period.toInt()}", fontSize = 18.sp)
                    Slider(
                        value = period,
                        onValueChange = { period = it },
                        valueRange = 1f..100f
                    )

                    // Overbought and Oversold Thresholds
                    Text("Overbought Threshold: $overboughtThreshold", fontSize = 18.sp)
                    Slider(
                        value = overboughtThreshold.toFloat(),
                        onValueChange = { overboughtThreshold = it.toInt() },
                        valueRange = 50f..100f
                    )

                    Text("Oversold Threshold: $oversoldThreshold", fontSize = 18.sp)
                    Slider(
                        value = oversoldThreshold.toFloat(),
                        onValueChange = { oversoldThreshold = it.toInt() },
                        valueRange = 0f..50f
                    )
                }
                StrategyType.CUSTOM -> {

                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Graph preview placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Graph Preview", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Save Button
            Button(
                onClick = { /* Save the settings */ },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Save Settings")
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    StrategyParametersScreen()
}