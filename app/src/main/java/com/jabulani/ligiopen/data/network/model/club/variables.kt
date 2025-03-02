package com.jabulani.ligiopen.data.network.model.club

import com.jabulani.ligiopen.data.network.model.player.players
import com.jabulani.ligiopen.data.network.model.file.fileData

val club = ClubDetails(
    clubId = 1,
    clubLogo = fileData,
    clubMainPhoto = fileData,
    name = "AFC Leopards",
    clubAbbreviation = "AFC FC",
    description = "This is AFC Leopards team",
    country = "Kenya",
    county = "Kakamega",
    town = "Kakamega town",
    startedOn = "2020-09-17",
    createdAt = "2025-02-07T08:02:01.878284",
    archived = false,
    archivedAt = null,
    players = players
)

val clubs = List(10) {index ->
    ClubDetails(
        clubId = 1 + index,
        clubLogo = fileData,
        clubMainPhoto = fileData,
        name = "AFC Leopards",
        clubAbbreviation = "AFC FC",
        description = "This is AFC Leopards team",
        country = "Kenya",
        county = "Kakamega",
        town = "Kakamega town",
        startedOn = "2020-09-17",
        createdAt = "2025-02-07T08:02:01.878284",
        archived = false,
        archivedAt = null,
        players = players
    )
}