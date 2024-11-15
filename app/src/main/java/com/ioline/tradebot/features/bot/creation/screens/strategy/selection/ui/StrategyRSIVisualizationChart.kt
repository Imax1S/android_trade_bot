package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.cartesianLayerPadding
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.decoration.HorizontalLine
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.component.LineComponent
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import kotlinx.coroutines.delay

@Composable
fun StrategyRSIVisualizationChart(
    prices: List<Int>,
    periodForSecondEMA: Float,
    periodForFirstEMA: Float
) {
    val modelProducer = remember { CartesianChartModelProducer() }
    val marker = rememberDefaultCartesianMarker(label = TextComponent())

    LaunchedEffect(periodForFirstEMA, periodForSecondEMA) {
        delay(100)
        modelProducer.runTransaction {
            lineSeries {
                series(prices.takeLast(10))
//                series(
//
//                )
//                series(
//
//                )
            }
        }
    }

    Chart(modelProducer, marker)
}

@Composable
private fun Chart(
    modelProducer: CartesianChartModelProducer,
    marker: DefaultCartesianMarker
) {
    CartesianChartHost(
        modifier = Modifier.fillMaxSize(),
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(
                LineCartesianLayer.LineProvider.series(
                    chartColors.map { color ->
                        LineCartesianLayer.rememberLine(
                            fill = remember { LineCartesianLayer.LineFill.single(fill(color)) },
                            areaFill = null,
                        )
                    }
                )
            ),
            startAxis = VerticalAxis.rememberStart(),
            bottomAxis = HorizontalAxis.rememberBottom(itemPlacer = HorizontalAxis.ItemPlacer.segmented()),
            marker = marker,
            layerPadding = cartesianLayerPadding(scalableStart = 16.dp, scalableEnd = 16.dp),
            legend = rememberLegend(),
            decorations = listOf(getViewHorizontalLineBottom(), getViewHorizontalLineTop()),
        ),
        modelProducer = modelProducer,
        zoomState = rememberVicoZoomState(zoomEnabled = false),
    )
}

private fun getViewHorizontalLineBottom() =
    HorizontalLine(
        y = { HORIZONTAL_LINE_Y_BOTTOM },
        line = LineComponent(HORIZONTAL_LINE_COLOR, HORIZONTAL_LINE_THICKNESS_DP),
        labelComponent =
        TextComponent(
            margins = Dimensions(HORIZONTAL_LINE_LABEL_MARGIN_DP),
            padding =
            Dimensions(
                HORIZONTAL_LINE_LABEL_HORIZONTAL_PADDING_DP,
                HORIZONTAL_LINE_LABEL_VERTICAL_PADDING_DP,
            ),
            background = ShapeComponent(HORIZONTAL_LINE_COLOR, CorneredShape.Pill),
        ),
    )

private fun getViewHorizontalLineTop() =
    HorizontalLine(
        y = { HORIZONTAL_LINE_Y_TOP },
        line = LineComponent(HORIZONTAL_LINE_COLOR, HORIZONTAL_LINE_THICKNESS_DP),
        labelComponent =
        TextComponent(
            margins = Dimensions(HORIZONTAL_LINE_LABEL_MARGIN_DP),
            padding =
            Dimensions(
                HORIZONTAL_LINE_LABEL_HORIZONTAL_PADDING_DP,
                HORIZONTAL_LINE_LABEL_VERTICAL_PADDING_DP,
            ),
            background = ShapeComponent(HORIZONTAL_LINE_COLOR, CorneredShape.Pill),
        ),
    )

private const val HORIZONTAL_LINE_Y_BOTTOM = 10.0
private const val HORIZONTAL_LINE_Y_TOP = 36.0
private const val HORIZONTAL_LINE_COLOR = -2893786
private const val HORIZONTAL_LINE_THICKNESS_DP = 2f
private const val HORIZONTAL_LINE_LABEL_HORIZONTAL_PADDING_DP = 8f
private const val HORIZONTAL_LINE_LABEL_VERTICAL_PADDING_DP = 2f
private const val HORIZONTAL_LINE_LABEL_MARGIN_DP = 4f

fun calculateRSI(data: List<Int>, period: Int): List<Double> {
    if (data.isEmpty() || period <= 0 || period > data.size) return listOf(0.0)

    val rsiValues = mutableListOf<Double>()

    // Начальные значения прироста и потерь
    var avgGain = 0.0
    var avgLoss = 0.0

    // Инициализация первой средней для gain и loss
    for (i in 1 until period) {
        val change = data[i] - data[i - 1]
        if (change > 0) avgGain += change else avgLoss -= change
    }
    avgGain /= period
    avgLoss /= period

    // Расчет RSI для первых `period` значений
    val rs = if (avgLoss == 0.0) Double.MAX_VALUE else avgGain / avgLoss
    rsiValues.add(100 - (100 / (1 + rs)))

    // Расчет RSI для остальных точек
    for (i in period until data.size) {
        val change = data[i] - data[i - 1]
        val gain = if (change > 0) change.toDouble() else 0.0
        val loss = if (change < 0) -change.toDouble() else 0.0

        avgGain = (avgGain * (period - 1) + gain) / period
        avgLoss = (avgLoss * (period - 1) + loss) / period

        val rsNext = if (avgLoss == 0.0) Double.MAX_VALUE else avgGain / avgLoss
        rsiValues.add(100 - (100 / (1 + rsNext)))
    }

    return rsiValues
}

private val chartColors = listOf(Color(0xffb983ff), Color(0xff91b1fd), Color(0xff8fdaff))