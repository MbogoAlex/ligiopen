package com.jabulani.ligiopen.ui.inapp.home

enum class HomeScreenTab {
    NEWS,
    SCORES,
    CLUBS,
    FIXTURES,
    FINANCING,
    PROFILE
}

data class HomeScreenTabItem(
    val name: String,
    val icon: Int,
    val tab: HomeScreenTab
)