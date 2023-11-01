package com.example.pokeclicker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeclicker.databinding.FragmentRecordsBinding
import com.example.pokeclicker.overview.OverviewViewModel
import com.example.pokeclicker.overview.OverviewViewModelFactory
import kotlinx.coroutines.launch

class RecordsFragment : Fragment() {

    private val viewModel: OverviewViewModel by activityViewModels {
        OverviewViewModelFactory(
            (activity?.application as PokeClickerApplication).database.pokemonDao()
        )
    }

    private lateinit var recyclerView: RecyclerView
    private var _binding: FragmentRecordsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recordsView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val itemListAdapter = ItemListAdapter()
        recyclerView.adapter = itemListAdapter

        lifecycle.coroutineScope.launch {
            viewModel.retrievePokemon().collect() {
                itemListAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}