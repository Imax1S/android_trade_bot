package com.ioline.tradebot.features.bot.overview.presenation

import com.ioline.tradebot.data.repository.bot.BotRepository
import com.ioline.tradebot.features.bot.overview.presenation.BotOverviewEvent.Domain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject

internal class BotOverviewActor @Inject constructor(
    private val botRepository: BotRepository,
) : Actor<BotReviewCommand, Domain> {

    override fun execute(
        command: BotReviewCommand
    ): Flow<Domain> = flow {
        when (command) {
            is BotReviewCommand.Init -> {
                botRepository.getBot(command.botId)
                    .catch { emit(Domain.Error(it.toString())) }
                    .collect {
                        if (it != null) {
                            emit(Domain.LoadData(it))
                        } else {
                            emit(Domain.Error("No data"))
                        }
                    }
            }
            is BotReviewCommand.RunBotOnHistoricalData -> {
                val result = botRepository.runBot(command.botId)

                emit(Domain.LoadRunResult(result))
            }
        }
    }
}