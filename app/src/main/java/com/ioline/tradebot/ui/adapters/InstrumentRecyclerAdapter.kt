package com.ioline.tradebot.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ioline.tradebot.data.models.Instrument
import com.ioline.tradebot.databinding.InstrumentItemBinding

class InstrumentRecyclerAdapter(val setupInstrument: (Instrument) -> Unit) :
    ListAdapter<Instrument, InstrumentRecyclerAdapter.InstrumentViewHolder>(DiffCallback) {
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Instrument>() {
            override fun areItemsTheSame(oldItem: Instrument, newItem: Instrument): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Instrument, newItem: Instrument): Boolean {
                return oldItem.uid == newItem.uid
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstrumentViewHolder {
        return InstrumentViewHolder(
            InstrumentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ),
            setupInstrument
        )
    }

    override fun onBindViewHolder(holder: InstrumentViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    inner class InstrumentViewHolder(
        private val binding: InstrumentItemBinding,
        setupInstrument: (Instrument) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(instrument: Instrument) {
            binding.instrumentName.text = instrument.name
            binding.instrumentPrice.text = instrument.ticker
            binding.instrumentSetupButton.setOnClickListener {
                setupInstrument(instrument)
            }
        }
    }
}