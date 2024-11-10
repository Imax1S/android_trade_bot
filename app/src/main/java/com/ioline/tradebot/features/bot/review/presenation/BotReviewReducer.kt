package com.ioline.tradebot.features.bot.review.presenation

import com.ioline.tradebot.features.bot.review.presenation.BotReviewEvent.Internal
import com.ioline.tradebot.features.bot.review.presenation.BotReviewEvent.Ui
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer
import com.ioline.tradebot.features.bot.review.presenation.BotReviewCommand as Command
import com.ioline.tradebot.features.bot.review.presenation.BotReviewEffect as Effect
import com.ioline.tradebot.features.bot.review.presenation.BotReviewEvent as Event
import com.ioline.tradebot.features.bot.review.presenation.BotReviewState as State

internal object BotReviewReducer :
    ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

    override fun Result.internal(event: Internal) = when (event) {
        else -> {}
    }

    override fun Result.ui(event: Ui) = when (event) {
        Ui.System.Init -> TODO()
        Ui.Click.Back -> effects {
            +Effect.Close
        }
        is Ui.Click.InteractWithChar -> TODO()
        is Ui.Click.OpenStrategy -> effects {
            +Effect.OpenStrategy
        }
    }
}