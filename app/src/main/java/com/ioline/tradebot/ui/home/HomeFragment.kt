package com.ioline.tradebot.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ioline.tradebot.databinding.FragmentHomeBinding
import com.ioline.tradebot.ui.adapters.HomeBotRecyclerAdapter
import com.ioline.tradebot.ui.bot.creation.BotCreationFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonHome.setOnClickListener {
            val newFragment = BotCreationFragment.newInstance()
            parentFragmentManager.beginTransaction()
                .replace(((view as ViewGroup).parent as View).id, newFragment)
                .addToBackStack(null)
                .commit()
        }

        val botAdapter = HomeBotRecyclerAdapter(
            {
                homeViewModel.runBot(it.copy(isActive = true))
            },
            {
                homeViewModel.runBot(it.copy(isActive = true))

            }
        )

        homeViewModel.bots.observe(viewLifecycleOwner) {
            binding.buttonHome.visibility = if (it.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
            botAdapter.submitList(it)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerView.adapter = botAdapter

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
