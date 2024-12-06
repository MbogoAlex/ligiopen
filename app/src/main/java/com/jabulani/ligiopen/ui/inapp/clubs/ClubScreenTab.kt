package com.jabulani.ligiopen.ui.inapp.clubs


enum class ClubScreenTab {
    INFO,
    NEWS,
    FIXTURES,
    SCORES
}

data class ClubScreenTabItem(
    val name: String,
    val icon: Int,
    val tab: ClubScreenTab
)

