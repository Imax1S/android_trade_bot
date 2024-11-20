package com.ioline.tradebot.features.bot.overview.presenation

import com.ioline.tradebot.data.models.Bot

internal sealed class BotOverviewEvent {
    sealed class Domain : BotOverviewEvent() {
        data class LoadData(val bot: Bot) : Domain()
        data class UpdateBot(val bot: Bot) : Domain()
        data class Error(val error: String) : Domain()
    }

    sealed class Ui : BotOverviewEvent() {
        object System {
            data class Init(val botId: String) : Ui()
        }

        object Click {
            data class InteractWithChar(val position: Int) : Ui()
            data class OpenStrategy(val param: String) : Ui()
            data class SelectChartPeriod(val period: ChartPeriod) : Ui()
            data object Back : Ui()
            data object MakeHistoricalLaunch : Ui()
        }
    }
}