package com.ioline.tradebot.features.bot.review.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.features.bot.review.presenation.BotReviewEvent
import com.ioline.tradebot.features.bot.review.presenation.BotReviewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BotReviewView(state: BotReviewState, onEvent: (BotReviewEvent) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.bot.name) },
                navigationIcon = {
                    IconButton(onClick = { "navController.popBackStack()" }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Richest king",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Bot with strategy lorem lorem",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Bot’s yield",
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "+59,35% for all time",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge,
                color = Color(0xFF4CAF50)
            )

            Spacer(modifier = Modifier.height(16.dp))

            YieldChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFFFFF0F5), RoundedCornerShape(8.dp))
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            StrategyCard(onEvent)

            Spacer(Modifier.weight(1f))

            Button(
                onClick = { onEvent(BotReviewEvent.Ui.Click.Back) },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors()
            ) {
                Text("Dont")
            }
        }
    }
}

@Composable
internal fun YieldChart(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val path = Path().apply {
            moveTo(0f, size.height)
            lineTo(size.width * 0.2f, size.height * 0.6f)
            lineTo(size.width * 0.4f, size.height * 0.8f)
            lineTo(size.width * 0.6f, size.height * 0.3f)
            lineTo(size.width * 0.8f, size.height * 0.5f)
            lineTo(size.width, size.height * 0.2f)
        }

        drawPath(
            path = path,
            color = Color.Black,
            style = Stroke(width = 4.dp.toPx())
        )

        // Рисуем оси
        drawLine(
            color = Color.Black,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = 2.dp.toPx()
        )
        drawLine(
            color = Color.Black,
            start = Offset(0f, size.height),
            end = Offset(0f, 0f),
            strokeWidth = 2.dp.toPx()
        )
    }
}

@Composable
internal fun StrategyCard(onEvent: (BotReviewEvent) -> Unit) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFF5F5FF),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Hamster strategy",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "That is a simple strate...",
                    color = Color.Gray
                )
            }
            IconButton(onClick = { onEvent(BotReviewEvent.Ui.Click.OpenStrategy("")) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Go to strategy")
            }
        }
    }
}

@Preview
@Composable
private fun BotPreview() {
    BotReviewView(
        BotReviewState(
            bot = Bot(
                id = "animal",
                name = "Dianna Gonzalez",
                strategy = null,
                isActive = false,
                instrumentsFIGI = listOf(),
                marketEnvironment = MarketEnvironment.MARKET,
                timeSettings = null,
                result = null
            )
        ),
        {}
    )
}