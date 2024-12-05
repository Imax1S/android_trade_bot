package com.ioline.tradebot.features.bot.creation.screens.strategy.selection.ui.visualization

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.presentation.StrategySelectionEvent
import com.ioline.tradebot.features.bot.creation.screens.strategy.selection.ui.YieldText
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
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
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.Legend
import com.patrykandpatrick.vico.core.common.LegendItem
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import kotlinx.coroutines.delay

private const val TAKE_LAST = 180

@Composable
internal fun StrategyCustomVisualization(
    prices: List<Int>,
    stopLoss: Int,
    stopGrowth: Int,
    onEvent: (StrategySelectionEvent) -> Unit
) {
    val modelProducer = remember { CartesianChartModelProducer() }
    val marker = rememberDefaultCartesianMarker(label = TextComponent())

    val profit = 0.0

    val startPrice = prices.takeLast(TAKE_LAST).first()

    val lossNumber = startPrice - startPrice / 100.0 * stopLoss
    val stopLossLineData = List(TAKE_LAST) {
        lossNumber
    }
    val growthNumber = startPrice + startPrice / 100.0 * stopGrowth

    Log.d("TAGA", "startPrice:$startPrice, loss: $lossNumber, growth: $growthNumber")
    val stopGrowthLineData = List(TAKE_LAST) {
        growthNumber
    }

    LaunchedEffect(prices, stopLoss, stopGrowth) {
        delay(100)
        modelProducer.runTransaction {
            lineSeries {
                series(prices.takeLast(TAKE_LAST))
                series(stopLossLineData)
                series(stopGrowthLineData)
            }
        }
    }

    Column {
        YieldText(profit, onEvent)

        Chart(modelProducer, marker)
    }
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
                    LineCartesianLayer.rememberLine(
                        fill = remember { LineCartesianLayer.LineFill.single(fill(chartColors[0])) },
                        areaFill = null,
                    ),
                    LineCartesianLayer.rememberLine(
                        fill = remember { LineCartesianLayer.LineFill.single(fill(chartColors[1])) },
                        areaFill = null,
                    ),
                    LineCartesianLayer.rememberLine(
                        fill = remember { LineCartesianLayer.LineFill.single(fill(chartColors[2])) },
                        areaFill = null,
                    ),
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
            }
        ),
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
                    label = "Stop Loss line",
                )
            )
            add(
                LegendItem(
                    icon = shapeComponent(chartColors[2], CorneredShape.Pill),
                    labelComponent = labelComponent,
                    label = "Stop Growth line",
                )
            )
        },
        padding = dimensions(top = 8.dp),
    )
}

private val chartColors = listOf(
    Color(0xFFF44336), //price color
    Color(0xFFE91E63), // stop loss
    Color(0xFF4CAF50), // stop growth
)