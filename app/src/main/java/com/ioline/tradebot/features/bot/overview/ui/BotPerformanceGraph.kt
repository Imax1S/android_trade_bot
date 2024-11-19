package com.ioline.tradebot.features.bot.overview.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.data.models.OperationMode
import com.ioline.tradebot.features.bot.overview.presenation.BotReviewState
import com.ioline.tradebot.features.bot.overview.presenation.ChartPeriod
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.ioline.tradebot.features.bot.overview.presenation.BotOverviewEvent as Event

@Composable
internal fun BotPerformanceGraph(
    state: BotReviewState,
    onEvent: (Event) -> Unit
) {
    Box(modifier = Modifier.wrapContentSize()) {
        if (state.dataForSelectedPeriod.isEmpty()) {
            EmptyChart(state.bot.marketEnvironment, onEvent)
        } else {
            BotPerformanceChart(state, onEvent)
        }
    }
}

@Composable
private fun EmptyChart(
    environment: MarketEnvironment,
    onEvent: (Event) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
    ) {
        Text("Нет данных для отображения")
        Spacer(modifier = Modifier.height(8.dp))
        if (environment == MarketEnvironment.HISTORICAL_DATA) {
            Button(onClick = {
                onEvent(Event.Ui.Click.MakeHistoricalLaunch)
            }) {
                Text("Запустить бота на исторических данных")
            }
        }
    }
}

@Composable
internal fun BotPerformanceChart(state: BotReviewState, onEvent: (Event) -> Unit) {
    val modelProducer = remember { CartesianChartModelProducer() }
    val marker = rememberDefaultCartesianMarker(label = TextComponent())

    LaunchedEffect(state.selectedPeriod) {
        modelProducer.runTransaction {
            lineSeries {
                series(state.dataForSelectedPeriod)
            }
        }
    }

    Column {
        LaunchedEffect(Unit) {
            modelProducer.runTransaction { lineSeries { series(4, 12, 8, 16) } }
        }
        CartesianChartHost(
            rememberCartesianChart(
                rememberLineCartesianLayer(),
                startAxis = VerticalAxis.rememberStart(),
                bottomAxis = HorizontalAxis.rememberBottom(),
            ),
            modelProducer,
        )
        Spacer(Modifier.size(8.dp))
        ChartPeriods(state.selectedPeriod) { period ->
            onEvent(Event.Ui.Click.SelectChartPeriod(period))
        }
    }
}

@Composable
internal fun ChartPeriods(selectedChart: ChartPeriod, onSelectPeriod: (ChartPeriod) -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(ChartPeriod.entries) { period ->
            Button(
                onClick = { onSelectPeriod(period) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedChart == period) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary
                    }
                )
            ) {
                Text(period.name, maxLines = 1)
            }
        }
    }
}

@Preview
@Composable
private fun BotPerformanceGraphPreview() {
    BotPerformanceGraph(state = BotReviewState(
        bot = Bot(
            id = "principes",
            name = "Agnes Alvarez",
            description = "graeco",
            strategy = null,
            isActive = false,
            instrumentsFIGI = listOf(),
            marketEnvironment = MarketEnvironment.MARKET,
            timeSettings = null,
            mode = OperationMode.MANUAL,
            result = null
        ),
        selectedPeriod = ChartPeriod.ALL,
        dataForSelectedPeriod = listOf(
            0.0,
            1.2,
            2.5,
            3.8,
            3.2,
            2.7,
            1.9,
            0.7,
            -0.5,
            -1.3,
            -1.7,
            -1.5,
            -0.8,
            0.3,
            1.4,
            2.3
        )
    ), onEvent = {})
}