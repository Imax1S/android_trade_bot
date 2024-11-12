package com.ioline.tradebot.features.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.R
import com.ioline.tradebot.features.home.presentation.HomeEvent

@Composable
internal fun HomeError(onEvent: (HomeEvent) -> Unit) {
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
            onClick = { onEvent(HomeEvent.Ui.ReloadClick) },
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
private fun HomeErrorPreview() {
    HomeError {}
}