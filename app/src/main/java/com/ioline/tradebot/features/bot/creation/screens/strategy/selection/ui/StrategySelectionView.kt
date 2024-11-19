package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.ui

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
import com.ioline.tradebot.data.models.strategy.Strategy
import com.ioline.tradebot.data.models.strategy.StrategyType
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StrategySelectionView(
    strategies: List<Strategy>,
    onEvent: (StrategySelectionEvent) -> Unit
) {
    var selectedStrategy by remember { mutableStateOf(StrategyType.EMA) }
    var period1 by remember { mutableFloatStateOf(10f) }
    var period2 by remember { mutableFloatStateOf(80f) }
    var overboughtThreshold by remember { mutableIntStateOf(70) }
    var oversoldThreshold by remember { mutableIntStateOf(30) }
    var crossoverEnabled by remember { mutableStateOf(false) }
    val prices = remember {
        List(100) {
            Random.nextInt(50)
        }
    }

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
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(stringResource(R.string.select_strategy_title))
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                strategies.forEach { strategy ->
                    Button(
                        onClick = { selectedStrategy = strategy.type },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedStrategy == strategy.type) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.secondary
                            }
                        )
                    ) {
                        Text(strategy.type.name, maxLines = 1)
                    }
                }
            }

            Text(stringResource(R.string.strategy_selection_description))
            Text(
                getStrategyDescription(selectedStrategy)
            )

            // Graph preview placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                when (selectedStrategy) {
                    StrategyType.EMA -> {
                        StrategyMAVisualizationGraph(
                            prices = prices,
                            periodForFirstEMA = period1,
                            periodForSecondEMA = period2,
                            isExponential = true
                        )
                    }
                    StrategyType.MA -> {
                        StrategyMAVisualizationGraph(
                            prices = prices,
                            periodForSecondEMA = period2,
                            isExponential = false,
                            periodForFirstEMA = period1
                        )
                    }
                    StrategyType.RSI -> {
                        StrategyRSIVisualizationChart(
                            prices = prices,
                            periodForFirstEMA = period1,
                            periodForSecondEMA = period2,
                        )
                    }
                    StrategyType.CUSTOM -> {

                    }
                }
            }

            when (selectedStrategy) {
                StrategyType.EMA, StrategyType.MA -> {
                    Text("Period: ${period1.toInt()}", fontSize = 18.sp)
                    Slider(
                        value = period1,
                        onValueChange = { period1 = it },
                        valueRange = 1f..100f
                    )

                    Text("Period: ${period2.toInt()}", fontSize = 18.sp)
                    Slider(
                        value = period2,
                        onValueChange = { period2 = it },
                        valueRange = 1f..100f
                    )

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
                    Text("RSI Period: ${period1.toInt()}", fontSize = 18.sp)
                    Slider(
                        value = oversoldThreshold.toFloat(),
                        onValueChange = { period1 = it },
                        valueRange = 1f..100f
                    )

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
                    var stopLoss by remember { mutableFloatStateOf(0f) }
                    var stopGrowth by remember { mutableFloatStateOf(0f) }

                    Text("Set Stop Loss: ${stopLoss.toInt()}%", fontSize = 18.sp)
                    Slider(
                        value = stopLoss,
                        onValueChange = { stopLoss = it },
                        valueRange = 0f..100f
                    )

                    Text("Set Stop Growth: ${stopGrowth.toInt()}%", fontSize = 18.sp)
                    Slider(
                        value = stopGrowth,
                        onValueChange = { stopGrowth = it },
                        valueRange = 0f..100f
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { onEvent(StrategySelectionEvent.Ui.Click.Next) },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            ) {
                Text("Save bot")
            }
        }
    }
}

@Composable
private fun getStrategyDescription(selectedStrategy: StrategyType) =
    when (selectedStrategy) {
        StrategyType.EMA -> stringResource(R.string.ema_description)
        StrategyType.RSI -> stringResource(R.string.rsi_description)
        StrategyType.CUSTOM -> stringResource(R.string.custom_description)
        StrategyType.MA -> stringResource(R.string.ma_description)
    }

@Preview()
@Composable
fun StrategySelectionScreenPreview() {
    StrategySelectionView(strategies = StrategyType.entries.map {
        Strategy(it, "description of $it", "", "")
    }, onEvent = {})
}
