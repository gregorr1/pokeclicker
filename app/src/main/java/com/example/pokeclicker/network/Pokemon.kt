package com.example.pokeclicker.network

import com.squareup.moshi.Json

data class Pokemon(
    val id: Int,
    val name: String,
    @Json(name = "sprites") val sprite: Sprites
)

fun Pokemon.createFormattedName(): String =
    if(name == "mr-mime") {
        "Mr. Mime"
    } else name[0].uppercase() + name.substring(1)

data class Sprites(
    @Json(name = "versions") val version: Versions
)

data class Versions(
    @Json(name = "generation-iii") val generationIII: GenerationIII
)

data class GenerationIII(
    @Json(name = "ruby-sapphire") val rubySapphire: RubySapphire
)

data class RubySapphire(
    @Json(name = "front_default") val frontDefault: String
)