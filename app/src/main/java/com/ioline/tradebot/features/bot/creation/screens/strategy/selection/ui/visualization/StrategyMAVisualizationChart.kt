package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.ui.visualization

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.ui.YieldText
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.component.shapeComponent
import com.patrykandpatrick.vico.compose.common.data.rememberExtraLambda
import com.patrykandpatrick.vico.compose.common.dimensions
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.rememberHorizontalLegend
import com.patrykandpatrick.vico.compose.common.vicoTheme
import com.patrykandpatrick.vico.core.cartesian.CartesianDrawingContext
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.core.cartesian.Zoom
import com.patrykandpatrick.vico.core.cartesian.axis.BaseAxis
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.Legend
import com.patrykandpatrick.vico.core.common.LegendItem
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import kotlinx.coroutines.delay

private const val TAKE_LAST = 180

@SuppressLint("RestrictedApi")
@Composable
internal fun StrategyMAVisualizationGraph(
    prices: List<Int>,
    periodForSecondEMA: Int,
    isExponential: Boolean,
    periodForFirstEMA: Int,
    onEvent: (StrategySelectionEvent) -> Unit
) {
    val modelProducer = remember { CartesianChartModelProducer() }
    val marker = rememberDefaultCartesianMarker(label = TextComponent())
    val shortMA = getDataForLine(
        prices,
        periodForFirstEMA,
        isExponential
    ).takeLast(TAKE_LAST)
    val longMA = getDataForLine(
        prices,
        periodForSecondEMA,
        isExponential
    ).takeLast(TAKE_LAST)
    val (actionsPointsToBuy, actionsPointsToSell) = getBuyAndSellActionList(
        shortEma = shortMA,
        longEma = longMA,
        prices = prices.takeLast(TAKE_LAST)
    )
    val tradingPoints = findTradingPoints(shortEma = shortMA, longEma = longMA).takeLast(TAKE_LAST)
    val profit = calculateProfit(
        prices.takeLast(TAKE_LAST),
        tradingPoints
    )

    LaunchedEffect(prices, periodForFirstEMA, periodForSecondEMA) {
        delay(100)
        modelProducer.runTransaction {
            lineSeries {
                series(prices.takeLast(TAKE_LAST))
                series(shortMA)
                series(longMA)
            }
            columnSeries {
                series(actionsPointsToBuy)
                series(actionsPointsToSell)
            }
        }
    }

    Column {
        YieldText(profit, onEvent)

        Chart(modelProducer, marker)
    }
}

@Preview
@Composable
private fun YieldTextPreview() {
    YieldText(0.1) {}
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
    marker: DefaultCartesianMarker,
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
            rememberColumnCartesianLayer(
                ColumnCartesianLayer.ColumnProvider.series(
                    actionPointsColor.map { color ->
                        rememberLineComponent(
                            color = color,
                            thickness = 18.dp,
                            shape = CorneredShape.rounded(allPercent = 40),
                        )
                    }
                )
            ),
            startAxis = VerticalAxis.rememberStart(
                horizontalLabelPosition = VerticalAxis.HorizontalLabelPosition.Inside,
            ),
            bottomAxis = HorizontalAxis.rememberBottom(
                itemPlacer = remember {
                    HorizontalAxis.ItemPlacer.aligned(spacing = 1)
                },
                size = remember { BaseAxis.Size.Auto() }
            ),
            marker = marker,
            legend = rememberLegend(),
        ),
        modelProducer = modelProducer,
        zoomState = rememberVicoZoomState(
            zoomEnabled = true,
            initialZoom = remember {
                Zoom.min(
                    Zoom.static(), Zoom.Content
                )
            }),
    )
}

@Composable
private fun rememberLegend(): Legend<CartesianMeasuringContext, CartesianDrawingContext> {
    val labelComponent = rememberTextComponent(vicoTheme.textColor)
    return rememberHorizontalLegend(
        items = rememberExtraLambda {
            add(
                LegendItem(
                    icon = shapeComponent(chartColors[0], CorneredShape.Pill),
                    labelComponent = labelComponent,
                    label = "Price",
                )
            )
            add(
                LegendItem(
                    icon = shapeComponent(chartColors[1], CorneredShape.Pill),
                    labelComponent = labelComponent,
                    label = "Short MA",
                )
            )
            add(
                LegendItem(
                    icon = shapeComponent(chartColors[2], CorneredShape.Pill),
                    labelComponent = labelComponent,
                    label = "Long MA",
                )
            )

            add(
                LegendItem(
                    icon = shapeComponent(actionPointsColor.first(), CorneredShape.Pill),
                    labelComponent = labelComponent,
                    label = "Buy action",
                )
            )

            add(
                LegendItem(
                    icon = shapeComponent(actionPointsColor.last(), CorneredShape.Pill),
                    labelComponent = labelComponent,
                    label = "Sell action",
                )
            )
        },
        padding = dimensions(top = 8.dp),
    )
}

fun getBuyAndSellActionList(
    shortEma: List<Double>,
    longEma: List<Double>,
    prices: List<Int>
): Pair<List<Double>, List<Double>> {
    if (shortEma.size != longEma.size) {
        throw IllegalArgumentException("Lists of short EMA and long EMA must have the same size.")
    }

    val tradingPointsToBuy = mutableListOf(0.0)
    val tradingPointsToSell = mutableListOf(0.0)

    for (i in 1 until shortEma.size) {
        val prevShort = shortEma[i - 1]
        val prevLong = longEma[i - 1]
        val currShort = shortEma[i]
        val currLong = longEma[i]

        // Покупка: короткая EMA пересекает длинную снизу вверх
        if (prevShort < prevLong && currShort > currLong) {
            tradingPointsToBuy.add(prices[i].toDouble())
            tradingPointsToSell.add(0.0)
        }
        // Продажа: короткая EMA пересекает длинную сверху вниз
        else if (prevShort > prevLong && currShort < currLong) {
            tradingPointsToSell.add(prices[i].toDouble())
            tradingPointsToBuy.add(0.0)
        } else {
            tradingPointsToBuy.add(0.0)
            tradingPointsToSell.add(0.0)
        }
    }

    return tradingPointsToBuy to tradingPointsToSell
}

private fun findTradingPoints(
    shortEma: List<Double>,
    longEma: List<Double>
): List<Pair<Int, String>> {
    if (shortEma.size != longEma.size) {
        throw IllegalArgumentException("Lists of short EMA and long EMA must have the same size.")
    }

    val tradingPoints = mutableListOf<Pair<Int, String>>()

    for (i in 1 until shortEma.size) {
        val prevShort = shortEma[i - 1]
        val prevLong = longEma[i - 1]
        val currShort = shortEma[i]
        val currLong = longEma[i]

        // Покупка: короткая EMA пересекает длинную снизу вверх
        if (prevShort < prevLong && currShort > currLong) {
            tradingPoints.add(i to "Buy")
        }
        // Продажа: короткая EMA пересекает длинную сверху вниз
        else if (prevShort > prevLong && currShort < currLong) {
            tradingPoints.add(i to "Sell")
        }
    }

    return tradingPoints
}

fun calculateProfit(prices: List<Int>, tradingPoints: List<Pair<Int, String>>): Double {
    var profit = 30
    var lastBuyPrice: Int? = null

    for ((index, action) in tradingPoints) {
        when (action) {
            "Buy" -> {
                // Зафиксировать цену покупки
                if (lastBuyPrice == null) { // Покупка возможна, только если ранее не было покупки
                    lastBuyPrice = prices[index]
                }
            }
            "Sell" -> {
                // Рассчитать доход, если была покупка
                if (lastBuyPrice != null) {
                    profit += prices[index] - lastBuyPrice
                    lastBuyPrice = null // Сбросить состояние после продажи
                }
            }
        }
    }

    return profit / 20.0
}

private val chartColors = listOf(
    Color(0xFFF44336), //price color
    Color(0xFF673AB7), //short MA
    Color(0xff8fdaff), //long MA
)

private val actionPointsColor = listOf(
    Color(0xFFE91E63), //buy
    Color(0xFF1C8F22), //sell
)

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
