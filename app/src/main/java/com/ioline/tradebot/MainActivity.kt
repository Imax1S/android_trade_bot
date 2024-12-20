package com.ioline.tradebot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.ioline.tradebot.common_ui.theme.TradeBotTheme
import com.ioline.tradebot.data.repository.bot.BotRepository
import com.ioline.tradebot.data.repository.instrument.InstrumentRepository
import com.ioline.tradebot.navigation.TradeBotNavHost
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var botRepository: BotRepository

    @Inject
    lateinit var instrumentRepository: InstrumentRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            var darkTheme by rememberSaveable { mutableStateOf(false) }

            TradeBotTheme(darkTheme = darkTheme) {
                Surface {
                    Scaffold { padding ->
                        TradeBotNavHost(
                            navController,
                            botRepository,
                            instrumentRepository,
                            darkTheme,
                            padding
                        ) {
                            darkTheme = !darkTheme
                        }
                    }
                }
            }
        }
    }
}