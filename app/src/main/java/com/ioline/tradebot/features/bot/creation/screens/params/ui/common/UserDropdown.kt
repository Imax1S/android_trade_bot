package com.ioline.tradebot.features.bot.creation.screens.params.ui.common

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun UserDropdown(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(true) }
    val items = listOf("A", "B", "C", "D", "E", "F")
    val disabledValue = "B"
    var selectedIndex by remember { mutableIntStateOf(0) }

    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        items.forEach {
            Text(it)
        }
    }

}

@Preview
@Composable
private fun UserDropdownReview() {
    UserDropdown()
}