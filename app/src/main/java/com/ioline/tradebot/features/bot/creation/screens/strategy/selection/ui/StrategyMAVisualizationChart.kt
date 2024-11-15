package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.cartesianLayerPadding
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.component.shapeComponent
import com.patrykandpatrick.vico.compose.common.data.rememberExtraLambda
import com.patrykandpatrick.vico.compose.common.dimensions
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.rememberVerticalLegend
import com.patrykandpatrick.vico.compose.common.shape.rounded
import com.patrykandpatrick.vico.compose.common.vicoTheme
import com.patrykandpatrick.vico.core.cartesian.CartesianDrawingContext
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.Legend
import com.patrykandpatrick.vico.core.common.LegendItem
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import kotlinx.coroutines.delay

@SuppressLint("RestrictedApi")
@Composable
fun StrategyMAVisualizationGraph(
    prices: List<Int>,
    periodForSecondEMA: Float,
    isExponential: Boolean,
    periodForFirstEMA: Float
) {
    val modelProducer = remember { CartesianChartModelProducer() }
    val marker = rememberDefaultCartesianMarker(label = TextComponent())

    LaunchedEffect(periodForFirstEMA, periodForSecondEMA) {
        delay(100)
        modelProducer.runTransaction {
            lineSeries {
                series(prices.takeLast(10))
                series(
                    getDataForLine(
                        prices,
                        periodForFirstEMA.toInt(),
                        isExponential
                    ).takeLast(10)
                )
                series(
                    getDataForLine(
                        prices,
                        periodForSecondEMA.toInt(),
                        isExponential
                    ).takeLast(10)
                )
            }
        }
    }

    Chart(modelProducer, marker)
}

fun getDataForLine(data: List<Int>, period: Int, isExponential: Boolean): List<Double> =
    if (isExponential) {
        calculateEMA(data, period)
    } else {
        calculateMA(data, period)
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
        ),
        modelProducer = modelProducer,
        zoomState = rememberVicoZoomState(zoomEnabled = false),
    )
}

@Composable
private fun rememberStartAxisLabel() =
    rememberAxisLabelComponent(
        color = Color.Black,
        margins = dimensions(4.dp),
        padding = dimensions(8.dp, 2.dp),
        background = rememberShapeComponent(Color(0xfffab94d), CorneredShape.rounded(4.dp)),
    )

@Composable
fun rememberLegend(): Legend<CartesianMeasuringContext, CartesianDrawingContext> {
    val labelComponent = rememberTextComponent(vicoTheme.textColor)
    val resources = LocalContext.current.resources
    return rememberVerticalLegend(
        items = rememberExtraLambda {
            chartColors.forEachIndexed { index, color ->
                add(
                    LegendItem(
                        icon = shapeComponent(color, CorneredShape.Pill),
                        labelComponent = labelComponent,
                        label = "$index",
                    )
                )
            }
        },
        padding = dimensions(top = 8.dp),
    )
}


private val chartColors = listOf(Color(0xffb983ff), Color(0xff91b1fd), Color(0xff8fdaff))

private fun calculateEMA(data: List<Int>, period: Int): List<Double> {
    if (data.isEmpty() || period <= 0 || period > data.size) return listOf(0.0)

    // Коэффициент сглаживания
    val k = 2.0 / (period + 1)
    val emaValues = mutableListOf<Double>()

    // Начинаем с расчета SMA для первых `period` значений как начальной точки
    val sma = data.take(period).average()
    emaValues.add(sma)

    // Расчет EMA для оставшихся точек
    for (i in period until data.size) {
        val ema = data[i] * k + emaValues.last() * (1 - k)
        emaValues.add(ema)
    }

    return emaValues
}

fun calculateMA(data: List<Int>, period: Int): List<Double> {
    if (data.isEmpty() || period <= 0 || period > data.size) return listOf(0.0)

    val maValues = mutableListOf<Double>()

    // Скользящее окно для расчета MA
    for (i in 0..data.size - period) {
        val window = data.subList(i, i + period)
        maValues.add(window.average())
    }

    return maValues
}