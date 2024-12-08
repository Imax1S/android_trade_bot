package com.ioline.tradebot.features.bot.creation.screens.params.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.R
import com.ioline.tradebot.data.models.Instrument
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.data.models.OperationMode
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationEvent
import com.ioline.tradebot.features.bot.creation.screens.params.presentation.BotCreationState

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
internal fun SearchTicketsField(state: BotCreationState, onEvent: (BotCreationEvent) -> Unit) {
    var searchText by remember { mutableStateOf("") }
    val suggestions = state.searchInstruments

    Column {
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onEvent(BotCreationEvent.Ui.Click.SearchInstrument(it))
            },
            label = { Text(stringResource(R.string.search_tickets_label)) },
            modifier = Modifier.fillMaxWidth(),
            isError = state.errorSelectedTickersValidation
        )
        Box(
            modifier = Modifier
                .background(Color.Gray)
                .padding(8.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.searchInstrumentError -> {
                    SearchInstrumentsError(onEvent)
                }
                state.searchInstrumentsLoading -> {
                    SearchInstrumentsLoading()
                }
                suggestions.isNotEmpty() -> {
                    LazyColumn(
                        modifier = Modifier
                            .heightIn(max = 96.dp)
                            .fillMaxWidth()
                    ) {
                        items(suggestions) { suggestion ->
                            Text(
                                text = "${suggestion.ticker} ${suggestion.price}р.",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onEvent(
                                            BotCreationEvent.Ui.Click.SelectInstrument(
                                                suggestion.ticker
                                            )
                                        )
                                    }
                                    .padding(8.dp)
                            )
                        }
                    }
                }
                else -> {
                    Text(stringResource(R.string.search_tickets_no_results))
                }
            }
        }
    }
}

@Composable
private fun SearchInstrumentsLoading() {
    Text(
        text = LocalContext.current.getString(R.string.home_loading),
        style = TextStyle(
            fontFamily = FontFamily.Monospace
        ),
    )
    Spacer(modifier = Modifier.padding(16.dp))
    CircularProgressIndicator(modifier = Modifier.size(40.dp))
}

@Composable
private fun SearchInstrumentsError(onEvent: (BotCreationEvent) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = LocalContext.current.getString(R.string.error_text),
            style = TextStyle(
                fontFamily = FontFamily.Monospace
            )
        )

        Spacer(Modifier.padding(16.dp))

        Button(
            onClick = { onEvent(BotCreationEvent.Ui.Click.RetrySearchInstrument) },
            modifier = Modifier
                .wrapContentSize(),
            colors = ButtonDefaults.buttonColors()
        ) {
            Text(stringResource(R.string.error_try))
        }
    }
}

@Preview
@Composable
private fun SearchTicketsWithResultPreview() {
    SearchTicketsField(state = BotCreationState(
        name = "Everette Holloway",
        description = "quem",
        marketEnvironment = MarketEnvironment.MARKET,
        mode = OperationMode.MANUAL,
        searchInstruments = instrumentsMock,
        selectedInstruments = listOf(),
        searchInstrumentsLoading = false,
        searchInstrumentError = false
    ), onEvent = {}
    )
}

@Preview
@Composable
private fun SearchTicketsNoResultPreview() {
    SearchTicketsField(
        state = BotCreationState(
            name = "Denver Pierce",
            description = "legere",
            marketEnvironment = MarketEnvironment.MARKET,
            mode = OperationMode.MANUAL,
            searchInstruments = listOf(),
            selectedInstruments = listOf(),
            searchInstrumentsLoading = false,
            searchInstrumentError = false
        )
    ) { }
}

@Preview
@Composable
private fun SearchTicketsErrorPreview() {
    SearchTicketsField(
        state = BotCreationState(
            name = "Noe Baxter",
            description = "ultrices",
            marketEnvironment = MarketEnvironment.MARKET,
            mode = OperationMode.MANUAL,
            searchInstruments = listOf(),
            selectedInstruments = listOf(),
            searchInstrumentsLoading = false,
            searchInstrumentError = true
        )
    ) { }
}

@Preview
@Composable
private fun SearchTicketsLoadingPreview() {
    SearchTicketsField(state = BotCreationState(
        name = "Emily Lopez",
        description = "deterruisset",
        marketEnvironment = MarketEnvironment.MARKET,
        mode = OperationMode.MANUAL,
        searchInstruments = listOf(),
        selectedInstruments = listOf(),
        searchInstrumentsLoading = true,
        searchInstrumentError = false
    ), onEvent = {}

    )
}

val instrumentsMock = listOf(
    Instrument(
        classCode = "AAPL",
        figi = "BBG000B9XRY4",
        first1dayCandleDate = "2000-01-01",
        first1minCandleDate = "2010-01-01",
        forIisFlag = true,
        instrumentType = "Equity",
        name = "Apple Inc.",
        ticker = "AAPL",
        uid = "uid1",
        price = 150.0
    ),
    Instrument(
        classCode = "GOOGL",
        figi = "BBG009S39JX6",
        first1dayCandleDate = "2004-08-19",
        first1minCandleDate = "2014-08-19",
        forIisFlag = false,
        instrumentType = "Equity",
        name = "Alphabet Inc.",
        ticker = "GOOGL",
        uid = "uid2",
        price = 2800.0
    ),
    Instrument(
        classCode = "TSLA",
        figi = "BBG000N9MNX3",
        first1dayCandleDate = "2010-06-29",
        first1minCandleDate = "2015-06-29",
        forIisFlag = true,
        instrumentType = "Equity",
        name = "Tesla Inc.",
        ticker = "TSLA",
        uid = "uid3",
        price = 800.0
    ),
    Instrument(
        classCode = "MSFT",
        figi = "BBG000BPH459",
        first1dayCandleDate = "1986-03-13",
        first1minCandleDate = "1990-01-01",
        forIisFlag = false,
        instrumentType = "Equity",
        name = "Microsoft Corp.",
        ticker = "MSFT",
        uid = "uid4",
        price = 299.0
    ),
    Instrument(
        classCode = "AMZN",
        figi = "BBG000BVPV84",
        first1dayCandleDate = "1997-05-15",
        first1minCandleDate = "2000-05-15",
        forIisFlag = true,
        instrumentType = "Equity",
        name = "Amazon.com Inc.",
        ticker = "AMZN",
        uid = "uid5",
        price = 3300.0
    )
)