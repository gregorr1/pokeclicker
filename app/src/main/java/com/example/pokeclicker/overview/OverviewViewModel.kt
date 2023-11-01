package com.example.pokeclicker.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pokeclicker.data.PokemonDao
import com.example.pokeclicker.data.PokemonEntity
import com.example.pokeclicker.network.PokeApi
import com.example.pokeclicker.network.Pokemon
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

enum class PokeApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel(private val pokemonDao: PokemonDao) : ViewModel() {
    private val _status = MutableLiveData<PokeApiStatus>()
    val status: LiveData<PokeApiStatus> = _status

    private val _sprites = MutableLiveData<List<Pokemon>>()
    val sprites: LiveData<List<Pokemon>> = _sprites

    init {
        getPokemonSprites()
    }

    fun retrievePokemon(): Flow<List<PokemonEntity>> = pokemonDao.getAllByClicks()

    fun upsertPokemon(pokemon: Pokemon) {
        viewModelScope.launch {
            // Retrieve the existing PokemonEntity (if it exists)
            val existingPokemonEntity = getPokemonEntityById(pokemon.id)
            // Increment the clicks count or create a new PokemonEntity if it doesn't exist
            if (existingPokemonEntity != null) {
                val updatedPokemonEntity = existingPokemonEntity
                    .copy(clicks = existingPokemonEntity.clicks + 1)
                pokemonDao.update(updatedPokemonEntity)
            } else {
                val newPokemonEntity = PokemonEntity(
                    id = pokemon.id,
                    name = pokemon.name,
                    clicks = 1
                )
                pokemonDao.insert(newPokemonEntity)
            }
        }
    }

    suspend fun getPokemonEntityById(id: Int): PokemonEntity? {
        return pokemonDao.getById(id)
    }

    private fun getPokemonSprites() {
        viewModelScope.launch {
            _status.value = PokeApiStatus.LOADING
            try {
                val pokemonList = (1..251).map {
                    async { PokeApi.retrofitService.getPokemon(it) } }
                _sprites.value = pokemonList.map { it.await() }
                _status.value = PokeApiStatus.DONE
            } catch (e: Exception) {
                _status.value = PokeApiStatus.ERROR
                _sprites.value = listOf()
            }
        }
    }
}

class OverviewViewModelFactory(private val pokemonDao: PokemonDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OverviewViewModel(pokemonDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}