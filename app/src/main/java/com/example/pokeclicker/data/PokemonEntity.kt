package com.example.pokeclicker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    var clicks: Int
)

fun PokemonEntity.createFormattedName(): String =
    if(name == "mr-mime") {
        "Mr. Mime"
    } else name[0].uppercase() + name.substring(1)