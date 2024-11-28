package com.jabulani.ligiopen.ui.inapp.playedMatches.lineup

enum class PlayerPosition {
    GOALKEEPER,
    DEFENDER,
    MIDFIELDER,
    FORWARD,
}

data class PlayerInLineup (
    val position: PlayerPosition,
    val name: String,
    val team: String,
    val home: Boolean,
    val number: Int,
    val substituted: Boolean,
    val yellowCard: Boolean,
    val redCard: Boolean,
    val scored: Boolean,
    val bench: Boolean
)

val playersInlineup = listOf(
    PlayerInLineup(
        position = PlayerPosition.GOALKEEPER,
        name = "John Doe",
        team = "NYC",
        number = 1,
        home = true,
        substituted = false,
        yellowCard = false,
        redCard = false,
        scored = false,
        bench = false
    ),
    PlayerInLineup(
        position = PlayerPosition.DEFENDER,
        name = "Michael Johnson",
        team = "NYC",
        number = 2,
        home = true,
        substituted = false,
        yellowCard = false,
        redCard = false,
        scored = false,
        bench = false
    ),
    PlayerInLineup(
        position = PlayerPosition.DEFENDER,
        name = "Chris Lee",
        team = "NYC",
        number = 3,
        home = true,
        substituted = false,
        yellowCard = false,
        redCard = false,
        scored = false,
        bench = false
    ),
    PlayerInLineup(
        position = PlayerPosition.DEFENDER,
        name = "David Kim",
        team = "NYC",
        number = 4,
        home = true,
        substituted = false,
        yellowCard = false,
        redCard = true,
        scored = false,
        bench = false
    ),
    PlayerInLineup(
        position = PlayerPosition.DEFENDER,
        name = "James Brown",
        team = "NYC",
        number = 5,
        home = true,
        substituted = false,
        yellowCard = true,
        redCard = false,
        scored = false,
        bench = false
    ),
    PlayerInLineup(
        position = PlayerPosition.MIDFIELDER,
        name = "Daniel Garcia",
        team = "NYC",
        number = 6,
        home = true,
        substituted = false,
        yellowCard = false,
        redCard = false,
        scored = false,
        bench = false
    ),
    PlayerInLineup(
        position = PlayerPosition.MIDFIELDER,
        name = "Kevin Martinez",
        team = "NYC",
        number = 8,
        home = true,
        substituted = false,
        yellowCard = false,
        redCard = false,
        scored = false,
        bench = false
    ),
    PlayerInLineup(
        position = PlayerPosition.MIDFIELDER,
        name = "Brian Anderson",
        team = "NYC",
        number = 10,
        home = true,
        substituted = true,
        yellowCard = true,
        redCard = false,
        scored = false,
        bench = false
    ),
    PlayerInLineup(
        position = PlayerPosition.FORWARD,
        name = "Robert White",
        team = "NYC",
        number = 7,
        home = true,
        substituted = true,
        yellowCard = false,
        redCard = false,
        scored = false,
        bench = false
    ),
    PlayerInLineup(
        position = PlayerPosition.FORWARD,
        name = "Michael Harris",
        team = "NYC",
        number = 9,
        home = true,
        substituted = false,
        yellowCard = false,
        redCard = false,
        scored = false,
        bench = false
    ),
    PlayerInLineup(
        position = PlayerPosition.DEFENDER,
        name = "Richard Clark",
        team = "NYC",
        number = 17,
        home = true,
        substituted = false,
        yellowCard = false,
        redCard = false,
        scored = false,
        bench = false
    ),
    PlayerInLineup(
        position = PlayerPosition.DEFENDER,
        name = "Thomas Lewis",
        team = "NYC",
        number = 18,
        home = true,
        substituted = false,
        yellowCard = false,
        redCard = false,
        scored = false,
        bench = true
    ),
    PlayerInLineup(
        position = PlayerPosition.DEFENDER,
        name = "James Brown",
        team = "NYC",
        number = 5,
        home = true,
        substituted = true,
        yellowCard = false,
        redCard = false,
        scored = false,
        bench = true
    ),
)