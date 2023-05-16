package com.ioline.tradebot.ui.bot.creation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.data.models.MarketEnvironment
import com.ioline.tradebot.data.models.OperationMode
import com.ioline.tradebot.databinding.FragmentBotCreationBinding
import com.ioline.tradebot.ui.HistoryStrategySetupFragment
import com.ioline.tradebot.ui.home.HomeViewModel


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BotCreationFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() =
            BotCreationFragment()
    }

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentBotCreationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBotCreationBinding.inflate(inflater, container, false)
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val root: View = binding.root

        setupSpinners()

        binding.buttonDone.setOnClickListener {

            val instrumentsString = binding.instrumentsTextField.text.toString()

            val environment: MarketEnvironment = enumValueOf(binding.environmentSpinner.selectedItem.toString())
            val bot = Bot(
                name = binding.botNameTextField.text.toString(),
//                mode = enumValueOf(binding.modeSpinner.selectedItem.toString()),
                marketEnvironment = environment
            )

            when (environment) {
                MarketEnvironment.MARKET, MarketEnvironment.SANDBOX -> {
                    parentFragmentManager.beginTransaction()
                        .replace(
                            ((view as ViewGroup).parent as View).id,
                            StrategySetUpFragment.newInstance(bot, instrumentsString)
                        )
                        .addToBackStack(null)
                        .commit()
                }
                MarketEnvironment.HISTORICAL_DATA -> {
                    parentFragmentManager.beginTransaction()
                        .replace(
                            ((view as ViewGroup).parent as View).id,
                            HistoryStrategySetupFragment.newInstance(bot, instrumentsString)
                        )
                        .addToBackStack(null)
                        .commit()
                }
            }
        }

        return root
    }



    private fun setupSpinners() {
        val marketEnvironmentArrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            MarketEnvironment.values().map {
                it.toString()
            })

        val operationModeArrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            OperationMode.values().map {
                it.toString()
            })

        binding.environmentSpinner.adapter = marketEnvironmentArrayAdapter
        binding.modeSpinner.adapter = operationModeArrayAdapter
    }
}
