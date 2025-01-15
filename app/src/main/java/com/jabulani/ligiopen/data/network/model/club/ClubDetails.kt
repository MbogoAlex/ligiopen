package com.jabulani.ligiopen.data.network.model.club

import kotlinx.serialization.Serializable

@Serializable
data class ClubDetails(
    val clubId: Int,
    val clubLogo: String?,
    val clubMainPhoto: String?,
    val name: String,
    val description: String,
    val country: String,
    val county: String,
    val town: String,
    val startedOn: String,
    val createdAt: String,
    val archived: Boolean,
    val archivedAt: String?,
    val players: List<PlayerDetails>,
    val files: List<String>
)
