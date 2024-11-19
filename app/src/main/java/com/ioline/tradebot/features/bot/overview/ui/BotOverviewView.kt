package com.ioline.tradebot.features.bot.overview.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.ioline.tradebot.R
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.Deal
import com.ioline.tradebot.data.models.Instrument
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.data.models.OrderType
import com.ioline.tradebot.data.models.strategy.StrategyType
import com.ioline.tradebot.features.bot.overview.presenation.BotOverviewEvent
import com.ioline.tradebot.features.bot.overview.presenation.BotReviewState

@Composable
internal fun BotOverviewView(state: BotReviewState, onEvent: (BotOverviewEvent) -> Unit) {
    Scaffold(
        floatingActionButton = {
            Row {
                FloatingActionButton(onClick = {}) {
                    ActionButton(isBotRunning = false, onActionClick = {})
                }
                Spacer(Modifier.size(16.dp))
                FloatingActionButton(onClick = {
                }) {
                    Icon(Icons.Default.Edit, stringResource(R.string.add_a_bot))
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                BotHeader(name = state.bot.name, description = state.bot.description)
            }
            item {
                BotPerformanceGraph(
                    state,
                    onEvent = { onEvent(it) }
                )
            }
            item {
                StrategyBanner(
                    strategyName = state.bot.strategy?.type?.name ?: "Strategy",
                    strategyDescription = getStrategyDescription(
                        state.bot.strategy?.type ?: StrategyType.CUSTOM
                    ),
                    onStrategyClick = { /* Переход на настройки стратегии */ }
                )
            }

            item {
                ExpandableLastDeals(
                    deals = state.bot.deals,
                    onViewFullHistoryClick = { /* Переход на полную историю сделок */ }
                )
            }

            item {
                ExpandableAssetsInWork(
                    assets = state.bot.assets
                )
            }

            item { Spacer(Modifier.size(68.dp)) }
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

@Composable
fun BotHeader(name: String, description: String) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Text(text = name, style = MaterialTheme.typography.headlineLarge)
        Text(text = description, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun StrategyBanner(strategyName: String, strategyDescription: String, onStrategyClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
            .clickable(onClick = onStrategyClick),
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = strategyName, style = MaterialTheme.typography.headlineLarge)
                Text(text = strategyDescription, style = MaterialTheme.typography.bodyLarge)
            }
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
        }
    }
}

private const val AMOUNT_OPERATIONS_TO_SHOW = 3

@Composable
fun ExpandableLastDeals(deals: List<Deal>, onViewFullHistoryClick: () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Last operations",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.clickable { isExpanded = !isExpanded }
            )

            if (deals.isEmpty()) {
                Text(
                    text = "There is no deals yet",
                    style = MaterialTheme.typography.bodyLarge,
                )
            } else {
                val amountDealsToShow = if (isExpanded) deals.size else AMOUNT_OPERATIONS_TO_SHOW
                deals.takeLast(amountDealsToShow).reversed().forEach { deal ->
                    Deal(deal)
                }

                if (deals.size > AMOUNT_OPERATIONS_TO_SHOW) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        onClick = {
                            isExpanded = !isExpanded
                        }
                    ) {
                        Text(
                            if (isExpanded) {
                                "Hide"
                            } else {
                                "Show All"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Deal(deal: Deal) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder
                    (LocalContext.current).data(data = "logoUrl")
                    .apply(block = fun ImageRequest.Builder.() {
                        placeholder(R.color.bot_card_background)
                        error(R.color.bot_card_background)
                        transformations(CircleCropTransformation())
                    }).build()
            ),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.size(8.dp))
        Column {
            Text(deal.asset.name)
            Text(deal.date)
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            deal.price.toString(),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
fun ExpandableAssetsInWork(assets: List<Instrument>) {
    var isExpanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Assets",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.clickable { isExpanded = !isExpanded }
            )
            if (assets.isEmpty()) {
                Text(
                    text = "There is no operations yet",
                    style = MaterialTheme.typography.bodyLarge,
                )
            } else {
                val amountDealsToShow = if (isExpanded) assets.size else AMOUNT_OPERATIONS_TO_SHOW
                assets.takeLast(amountDealsToShow).reversed().forEach { asset ->
                    Asset(asset)
                }

                if (assets.size > AMOUNT_OPERATIONS_TO_SHOW) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        onClick = {
                            isExpanded = !isExpanded
                        }
                    ) {
                        Text(
                            if (isExpanded) {
                                "Hide"
                            } else {
                                "Show All"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Asset(asset: Instrument) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder
                    (LocalContext.current).data(data = "logoUrl")
                    .apply(block = fun ImageRequest.Builder.() {
                        placeholder(R.color.bot_card_background)
                        error(R.color.bot_card_background)
                        transformations(CircleCropTransformation())
                    }).build()
            ),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.size(8.dp))
        Column {
            Text(asset.name)
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            asset.price.toString(),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
fun ActionButton(isBotRunning: Boolean, onActionClick: () -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .width(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isBotRunning) "Done" else "Launch bot",
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Preview
@Composable
private fun BotPreview() {
    val asset = Instrument(
        classCode = "conubia",
        figi = "semper",
        first1dayCandleDate = "viris",
        first1minCandleDate = "sale",
        forIisFlag = false,
        instrumentKind = "molestiae",
        instrumentType = "nobis",
        isin = "ac",
        name = "Noah Hooper",
        positionUid = "audire",
        ticker = "fames",
        uid = "gubergren",
        weekendFlag = false,
        price = 22.23
    )
    BotOverviewView(
        BotReviewState(
            bot = Bot(
                id = "animal",
                name = "Dianna Gonzalez",
                strategy = null,
                isActive = false,
                instrumentsFIGI = listOf(),
                marketEnvironment = MarketEnvironment.MARKET,
                timeSettings = null,
                result = null,
                deals = listOf(
                    Deal(
                        type = OrderType.SELL, asset = asset, price = 18.19, date = "nullam"
                    ),
                    Deal(
                        type = OrderType.SELL, asset = asset, price = 18.19, date = "nullam"
                    ),
                    Deal(
                        type = OrderType.BUY, asset = asset, price = 18.19, date = "nullam"
                    ),
                    Deal(
                        type = OrderType.BUY, asset = asset, price = 18.19, date = "nullam"
                    ),
                ),
                assets = listOf(asset, asset, asset, asset)
            ),
            dataForSelectedPeriod = listOf(0.0)
        )
    ) {}
}