package com.jabulani.ligiopen.data.network.model.club

import kotlinx.serialization.Serializable

@Serializable
data class PlayerResponseBody(
    val statusCode: Int,
    val message: String,
    val data: PlayerDetails
)
