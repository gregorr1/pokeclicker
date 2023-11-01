package com.example.pokeclicker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pokeclicker.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private var binding: FragmentStartBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentStartBinding.inflate(
            inflater, container, false)
        binding = fragmentBinding
        binding?.clickButton?.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_pokemonFragment)
        }
        binding?.recordsButton?.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_recordsFragment)
        }
        return fragmentBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}