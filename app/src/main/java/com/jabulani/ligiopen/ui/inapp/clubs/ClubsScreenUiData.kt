package com.jabulani.ligiopen.ui.inapp.clubs

import com.jabulani.ligiopen.data.db.model.UserAccount
import com.jabulani.ligiopen.data.network.model.club.ClubDetails

data class ClubsScreenUiData(
    val userAccount: UserAccount = UserAccount(0, "", "", "", "", "", ""),
    val clubs: List<ClubDetails> = emptyList(),
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL,
)
