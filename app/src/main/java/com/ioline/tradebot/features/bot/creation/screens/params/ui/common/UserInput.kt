package com.ioline.tradebot.features.bot.creation.screens.params.ui.common

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UserInput(label: String, placeholder: String) {
    val text = remember {
        mutableStateOf("")
    }
    TextField(
        value = text.value,
        onValueChange = { newText ->
            text.value = newText
        },
        textStyle = TextStyle(
            fontFamily = FontFamily.Monospace
        ),
        label = { Text(text = label, fontFamily = FontFamily.Monospace) },
        placeholder = { Text(text = placeholder, fontFamily = FontFamily.Monospace) },
        shape = TextFieldDefaults.outlinedShape
    )
}

@Preview
@Composable
fun UserInputPreview() {
    UserInput("Preview", "preview")
}
