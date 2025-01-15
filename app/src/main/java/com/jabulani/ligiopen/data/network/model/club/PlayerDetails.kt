package com.jabulani.ligiopen.data.network.model.club

import kotlinx.serialization.Serializable

@Serializable
data class PlayerDetails(
    val playerId: Int,
    val mainPhoto: String?,
    val username: String,
    val number: Int,
    val playerPosition: String,
    val age: Int,
    val height: Double,
    val weight: Double,
    val country: String,
    val county: String,
    val town: String,
    val clubId: Int?,
    val files: List<String>
)
