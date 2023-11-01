package com.example.pokeclicker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Upsert
    suspend fun upsert(pokemonEntity: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pokemonEntity: PokemonEntity)

    @Update
    suspend fun update(pokemonEntity: PokemonEntity)

    @Query("SELECT * from pokemon WHERE id = :id" )
    suspend fun getById(id: Int): PokemonEntity?

    @Query("SELECT * FROM pokemon ORDER BY clicks DESC")
    fun getAllByClicks(): Flow<List<PokemonEntity>>
}