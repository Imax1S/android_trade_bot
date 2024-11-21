package com.ioline.tradebot.features.bot.overview.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.ioline.tradebot.R
import com.ioline.tradebot.data.models.Instrument
import com.ioline.tradebot.data.models.Operation
import com.ioline.tradebot.data.models.OrderType
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun Deal(operation: Operation) {
    Column {
        Text(
            text = getDateComponents(operation.date),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.size(8.dp))
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(40.dp)
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
                Text(operation.asset.name.take(3))
            }

            Spacer(Modifier.size(8.dp))
            Column {
                Text("Amount: " + operation.size)
                Text("Price: " + "%.2f".format(operation.price / operation.size))
            }
            Spacer(modifier = Modifier.weight(1f))
            if (operation.pnlValue > 0) {
                Text(
                    "%.2f".format(operation.pnlValue),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF4CAF50)
                )
            }
        }
    }
}

fun getDateComponents(dateString: String): String {
    val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z")
    val parsedDate = ZonedDateTime.parse(dateString, formatter)
    val day = parsedDate.dayOfMonth
    val month = parsedDate.month
    val year = parsedDate.year
    return "$day $month $year"
}

@Preview
@Composable
private fun DealPreview() {
    val asset = Instrument(
        classCode = "conubia",
        figi = "semper",
        first1dayCandleDate = "viris",
        first1minCandleDate = "sale",
        forIisFlag = false,
        instrumentType = "nobis",
        name = "Noah Hooper",
        ticker = "fames",
        uid = "gubergren",
        price = 22.23
    )
    Deal(
        Operation(
            type = OrderType.SELL,
            asset = asset,
            price = 18.19,
            date = "28.10.2001",
            size = 3,
            pnlValue = 2.3,
        )
    )
}