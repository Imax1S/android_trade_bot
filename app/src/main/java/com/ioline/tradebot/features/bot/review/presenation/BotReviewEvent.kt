package com.ioline.tradebot.features.bot.review.presenation

internal sealed class BotReviewEvent {
    sealed class Internal : BotReviewEvent() {
    }

    sealed class Ui : BotReviewEvent() {
        object System {
            data object Init : Ui()
        }

        object Click {
            data class InteractWithChar(val position: Int) : Ui()
            data class OpenStrategy(val param: String) : Ui()
            data object Back : Ui()
        }

        object Action {
            // your code
        }
    }
}