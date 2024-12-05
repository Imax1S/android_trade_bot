package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.R
import com.ioline.tradebot.data.models.strategy.Strategy
import com.ioline.tradebot.data.models.strategy.StrategyType
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StrategySelectionView(
    state: StrategySelectionState,
    onEvent: (StrategySelectionEvent) -> Unit
) {
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                StrategySelector(state, state.selectedStrategy) { onEvent(it) }
            }

            item {
                Text(
                    stringResource(R.string.strategy_selection_description),
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    getStrategyDescription(state.selectedStrategy)
                )
            }

            if (state.randomData.isNotEmpty()) {
                item {
                    StrategyDemo(
                        state = state,
                        onEvent = onEvent
                    )
                }
            }

            item {
                StrategyParams(
                    selectedStrategy = state.selectedStrategy,
                    MAPeriod1 = state.MAPeriod1,
                    MAPeriod2 = state.MAPeriod2,
                    rsiPeriod = state.RSIPeriod,
                    oversoldThreshold = state.oversoldThreshold,
                    overboughtThreshold = state.overboughtThreshold
                ) {
                    onEvent(it)
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { onEvent(StrategySelectionEvent.Ui.Click.Next) },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(stringResource(R.string.strategy_selection_button_text))
                }
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
    StrategySelectionView(
        state = StrategySelectionState(
            botId = "",
            strategies = StrategyType.entries.map {
                Strategy(it, "description of $it", "", "")
            }
        ), onEvent = {})
}