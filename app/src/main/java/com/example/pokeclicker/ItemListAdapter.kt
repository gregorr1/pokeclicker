package com.example.pokeclicker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeclicker.data.PokemonEntity
import com.example.pokeclicker.data.createFormattedName
import com.example.pokeclicker.databinding.ItemListItemBinding

class ItemListAdapter:
    ListAdapter<PokemonEntity, ItemListAdapter.ItemViewHolder>(DiffCallback) {

    class ItemViewHolder(private var binding: ItemListItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bindEntity(pokemonEntity: PokemonEntity) {
            binding.listPokemonNumber.text = pokemonEntity.id.toString()
            binding.listPokemonName.text = pokemonEntity.createFormattedName()
            binding.listPokemonClicks.text = pokemonEntity.clicks.toString()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PokemonEntity>() {
        override fun areItemsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val pokemonEntity = getItem(position)
        holder.bindEntity(pokemonEntity)
    }
}