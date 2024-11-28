package com.jabulani.ligiopen.ui.inapp.playedMatches

enum class HighlightsScreenTabs {
    SUMMARY,
    TIMELINE,
    LINEUPS,
    STATS
}

data class HighlightsScreenTabItem(
    val name: String,
    val icon: Int,
    val tab: HighlightsScreenTabs
)
