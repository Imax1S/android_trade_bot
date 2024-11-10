package com.ioline.tradebot.di

import com.ioline.tradebot.data.repository.bot.BotRepository
import com.ioline.tradebot.data.repository.bot.BotRepositoryImpl
import com.ioline.tradebot.data.repository.instrument.InstrumentRepository
import com.ioline.tradebot.data.repository.instrument.InstrumentRepositoryImpl
import com.ioline.tradebot.data.source.local.LocalBotDataSource
import com.ioline.tradebot.data.source.local.LocalBotDataSourceImpl
import com.ioline.tradebot.data.source.remote.RemoteBotDataSource
import com.ioline.tradebot.data.source.remote.RemoteBotDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    fun provideBotRepository(
        localBotDataSource: LocalBotDataSource,
        remoteBotDataSource: RemoteBotDataSource
    ): BotRepository {
        return BotRepositoryImpl(
            localBotDataSource,
            remoteBotDataSource
        )
    }

    @Provides
    fun provideLocalBotDataSource(): LocalBotDataSource = LocalBotDataSourceImpl()

    @Provides
    fun provideRemoteBotDataSource(): RemoteBotDataSource = RemoteBotDataSourceImpl()

    @Provides
    fun provideInstrumentRepository(): InstrumentRepository = InstrumentRepositoryImpl()
}