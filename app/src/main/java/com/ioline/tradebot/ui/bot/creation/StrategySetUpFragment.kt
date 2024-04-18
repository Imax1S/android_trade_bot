package com.ioline.tradebot.ui.bot.creation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.databinding.FragmentStrategySetUpBinding
import com.ioline.tradebot.getBotSerializable
import com.ioline.tradebot.ui.adapters.InstrumentRecyclerAdapter
import com.ioline.tradebot.ui.home.HomeViewModel
import java.io.Serializable


class StrategySetUpFragment : Fragment() {
    companion object {
        private const val ARG_BOT = "bot"
        private const val ARG_INSTRUMENTS = "instruments"

        @JvmStatic
        fun newInstance(bot: Serializable, instrumentsText: String) =
            StrategySetUpFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_BOT, bot)
                    putString(ARG_INSTRUMENTS, instrumentsText)
                }
            }
    }
    private val homeViewModel: HomeViewModel by activityViewModels()
    private var bot: Bot? = null
    private var instrumentsText: String? = null

    private var _binding: FragmentStrategySetUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bot = it.getBotSerializable(ARG_BOT)
            instrumentsText = it.getString(ARG_INSTRUMENTS)
        }

        setFragmentResultListener(SellBuyConditionsFragment.RESULT_STRATEGY) {requestKey, bundle ->  
//            val strategy = bundle.getInstrumentSerializable() добавить получение обратно
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStrategySetUpBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupInstrumentRecycler()

        binding.buttonDone.setOnClickListener {
            homeViewModel.saveBot(bot)
            //navigate to home
        }

        return root
    }


    private fun setupInstrumentRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val instrumentAdapter = InstrumentRecyclerAdapter { instrument ->
            val newFragment = SellBuyConditionsFragment.newInstance(instrument)
            parentFragmentManager.beginTransaction()
                .add(newFragment, "SellAndBuy")
                .commit()
        }

        homeViewModel.instruments.observe(viewLifecycleOwner) {
            instrumentAdapter.submitList(it)
        }

        homeViewModel.setupInstruments(instrumentsText)


        binding.recyclerView.adapter = instrumentAdapter
    }
 }
