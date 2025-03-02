package com.jabulani.ligiopen.data.network.model.match.events

import com.jabulani.ligiopen.data.network.model.file.FileData
import kotlinx.serialization.Serializable

@Serializable
data class SubstitutionEventData(
    val title: String,
    val summary: String,
    val minute: String,
    val matchEventType: MatchEventType,
    val mainPlayerId: Int?,
    val subbedOutPlayerId: Int?,
    val files: List<FileData>
)
