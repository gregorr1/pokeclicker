package com.example.pokeclicker.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.pokeclicker.PokeClickerApplication
import com.example.pokeclicker.databinding.FragmentOverviewBinding
import com.example.pokeclicker.network.createFormattedName
import com.google.android.material.snackbar.Snackbar

class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by activityViewModels {
        OverviewViewModelFactory(
            (activity?.application as PokeClickerApplication).database.pokemonDao()
        )
    }

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ImageGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        adapter = ImageGridAdapter {
            viewModel.upsertPokemon(it)
            Snackbar.make(
                view,
                "You clicked ${it.createFormattedName()}.",
                Snackbar.LENGTH_SHORT
            )
                .show()
        }
        binding.photosGrid.adapter = adapter
    }
}