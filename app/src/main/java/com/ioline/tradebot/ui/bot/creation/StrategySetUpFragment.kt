package com.ioline.tradebot.ui.bot.creation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ioline.tradebot.R
import com.ioline.tradebot.data.models.Bot
import com.ioline.tradebot.databinding.FragmentStrategySetUpBinding
import com.ioline.tradebot.getBotSerializable
import com.ioline.tradebot.ui.adapters.InstrumentRecyclerAdapter
import com.ioline.tradebot.ui.home.HomeViewModel

private const val ARG_BOT = "bot"
private const val ARG_PARAM2 = "param2"

class StrategySetUpFragment : Fragment() {
    private var bot: Bot? = null
    private var param2: String? = null
    private var _binding: FragmentStrategySetUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bot = it.getBotSerializable(ARG_BOT)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentStrategySetUpBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val instrumentAdapter = InstrumentRecyclerAdapter()
        instrumentAdapter.submitList(bot?.instruments)
        binding.recyclerView.adapter = instrumentAdapter

        binding.buttonDone.setOnClickListener {
            homeViewModel.saveBot(bot)
            findNavController().navigate(R.id.action_strategySetUpFragment_to_navigation_home)
        }

        return root
    }
}