package com.ioline.tradebot.features.bot.overview.presenation

import com.ioline.tradebot.data.models.Bot

internal sealed class BotOverviewEvent {
    sealed class Internal : BotOverviewEvent() {
    }

    sealed class Ui : BotOverviewEvent() {
        object System {
            data class Init(val bot: Bot) : Ui()
        }

        object Click {
            data class InteractWithChar(val position: Int) : Ui()
            data class OpenStrategy(val param: String) : Ui()
            data class SelectChartPeriod(val period: ChartPeriod) : Ui()
            data object Back : Ui()
            data object MakeHistoricalLaunch : Ui()
        }

        object Action {
            // your code
        }
    }
}