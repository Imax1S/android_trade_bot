package com.ioline.tradebot.ui.bot.creation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.ioline.tradebot.data.models.Instrument
import com.ioline.tradebot.data.models.strategy.StrategyManual
import com.ioline.tradebot.databinding.FragmentSellBuyConditionsBinding
import com.ioline.tradebot.getInstrumentSerializable
import java.io.Serializable


class SellBuyConditionsFragment : Fragment() {
    companion object {
        private const val ARG_INSTRUMENT = "instrument"
        const val ARG_STRATEGY = "strategy"
        const val RESULT_STRATEGY = "result_strategy"

        @JvmStatic
        fun newInstance(instrument: Serializable) =
            SellBuyConditionsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_INSTRUMENT, instrument)
                }
            }
    }
    private var _binding: FragmentSellBuyConditionsBinding? = null
    private val binding get() = _binding!!
    private var instrument: Instrument? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            instrument = it.getInstrumentSerializable(ARG_INSTRUMENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSellBuyConditionsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonDone.setOnClickListener {
            val strategy = StrategyManual(
                binding.sellDropPerc.text.toString().toDouble(),
                binding.sellIncreasePerc.text.toString().toDouble(),
                binding.buyDropPerc.text.toString().toDouble()
            )
            // Use the Kotlin extension in the fragment-ktx artifact
            setFragmentResult(RESULT_STRATEGY, bundleOf(ARG_STRATEGY to strategy))
            parentFragmentManager.popBackStack()

        }
        return root
    }

}