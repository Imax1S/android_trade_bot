package com.ioline.tradebot.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.CandleInterval
import com.ioline.tradebot.data.models.strategy.Strategy
import com.ioline.tradebot.data.models.strategy.StrategyType
import com.ioline.tradebot.data.models.TimePeriod
import com.ioline.tradebot.data.models.TimeSettings
import com.ioline.tradebot.databinding.FragmentHistoryStrategySetupBinding
import com.ioline.tradebot.getBotSerializable
import com.ioline.tradebot.ui.adapters.InstrumentRecyclerAdapter
import com.ioline.tradebot.ui.home.HomeViewModel
import java.io.Serializable


class HistoryStrategySetupFragment : Fragment() {
    companion object {
        private const val ARG_BOT = "bot"
        private const val ARG_INSTRUMENTS = "instruments"
        @JvmStatic
        fun newInstance(bot: Serializable, instrumentsText: String) =
            HistoryStrategySetupFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_BOT, bot)
                    putString(ARG_INSTRUMENTS, instrumentsText)
                }
            }
    }

    private var bot: Bot? = null
    private var instrumentsText: String? = null
    private val homeViewModel: HomeViewModel by activityViewModels()


    private var _binding: FragmentHistoryStrategySetupBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bot = it.getBotSerializable(ARG_BOT)
            instrumentsText = it.getString(ARG_INSTRUMENTS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryStrategySetupBinding.inflate(
            inflater,
            container,
            false
        )
        val root: View = binding.root

        setupInstrumentRecycler()

        val strategyTypeArrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            StrategyType.values().map {
                it.toString()
            })

        val intervalTypeArrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            CandleInterval.values().map {
                it.toString()
            })

        val timePeriodTypeArrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            TimePeriod.values().map {
                it.toString()
            })

        binding.strategyTypeSpinner.adapter = strategyTypeArrayAdapter
        binding.intervalSpinner.adapter = intervalTypeArrayAdapter
        binding.timePeriod.adapter = timePeriodTypeArrayAdapter

        binding.strategyTypeSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val strategyType: StrategyType = enumValueOf(binding.strategyTypeSpinner.selectedItem.toString())

                when (strategyType) {
                    StrategyType.EMA -> {
                        binding.param1.hint = "Low Threshold"
                        binding.param2.hint = "High Threshold"
                    }
                    StrategyType.RSI -> {
                        binding.param1.hint = "Fast Period"
                        binding.param2.hint = "Slow Period"
                    }
                    StrategyType.CUSTOM -> {}
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }
        binding.buttonDone.setOnClickListener {
            val strategy = Strategy(
                type = enumValueOf(binding.strategyTypeSpinner.selectedItem.toString()),
                param1 = binding.param1.text.toString(),
                param2 = binding.param2.text.toString(),
            )
            val timeSettings = TimeSettings(
                interval = enumValueOf(binding.intervalSpinner.selectedItem.toString()),
                start = binding.timeNumber.text.toString(),
                end = "",
                period = enumValueOf(binding.timePeriod.selectedItem.toString())
            )
            bot = bot?.copy(
                strategy = strategy,
                timeSettings = timeSettings
            )
            homeViewModel.createBot(bot)
            //navigate to home

        }
        return root
    }

    private fun setupInstrumentRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val instrumentAdapter = InstrumentRecyclerAdapter {}

        homeViewModel.instruments.observe(viewLifecycleOwner) {
            instrumentAdapter.submitList(it)
        }

        homeViewModel.setupInstruments(instrumentsText)


        binding.recyclerView.adapter = instrumentAdapter
    }
}