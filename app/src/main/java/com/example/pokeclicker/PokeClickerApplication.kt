package com.example.pokeclicker

import android.app.Application
import com.example.pokeclicker.data.PokemonRoomDatabase

class PokeClickerApplication: Application() {
    val database: PokemonRoomDatabase by lazy { PokemonRoomDatabase.getDatabase(this) }
}