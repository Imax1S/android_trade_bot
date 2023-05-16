package com.ioline.tradebot.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.databinding.BotCardHomeBinding

class HomeBotRecyclerAdapter(val openBot: (Bot) -> Unit, val runBot: (Bot) -> Unit) :
    ListAdapter<Bot, HomeBotRecyclerAdapter.HomeBotViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Bot>() {
            override fun areItemsTheSame(oldItem: Bot, newItem: Bot): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Bot, newItem: Bot): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBotViewHolder {
        return HomeBotViewHolder(
            BotCardHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ),
            openBot,
            runBot
        )
    }

    override fun onBindViewHolder(holder: HomeBotViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    inner class HomeBotViewHolder(
        val binding: BotCardHomeBinding,
        openBot: (Bot) -> Unit,
        runBot: (Bot) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bot: Bot) {
            binding.botSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    runBot(bot)
                }
            }
            if (bot.result != null) {
                val growth =
                    if (bot.result.growth > 0) "+${bot.result.growth}" else "-${bot.result.growth}"
                binding.botYield.text = growth
                binding.botBalance.text = bot.result.finalBalance.toString()
            }
            binding.botName.text = bot.name
            binding.botDescription.text =
                "Bot on ${bot.marketEnvironment.value} " +
                        "\nwith strategy: ${bot.strategy?.type}" +
                        " (param1: ${bot.strategy?.param1}, param2: ${bot.strategy?.param2})" +
                        if (bot.marketEnvironment == MarketEnvironment.HISTORICAL_DATA) "\nperiod: last ${bot.timeSettings?.start} ${bot.timeSettings?.period}" else ""
            binding.botInstruments.text = bot.instrumentsFIGI.joinToString(" ")
            binding.botSwitch.isActivated = bot.isActive
        }
    }
}