package com.ioline.tradebot.features.home.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.R

@Composable
internal fun HomeLoading() {
    Text(
        text = LocalContext.current.getString(R.string.home_loading),
        style = TextStyle(
            fontFamily = FontFamily.Monospace
        ),
    )
    Spacer(modifier = Modifier.padding(16.dp))
    CircularProgressIndicator(modifier = Modifier.size(40.dp))
}