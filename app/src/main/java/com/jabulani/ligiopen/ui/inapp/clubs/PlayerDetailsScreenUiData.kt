package com.jabulani.ligiopen.ui.inapp.clubs

import com.jabulani.ligiopen.data.db.model.UserAccount
import com.jabulani.ligiopen.data.network.model.club.ClubDetails
import com.jabulani.ligiopen.data.network.model.club.PlayerDetails

data class PlayerDetailsScreenUiData(
    val userAccount: UserAccount = UserAccount(0, "", "", "", "", "", ""),
    val playerDetails: PlayerDetails = PlayerDetails(0, "", "", 0, "", 0, 0.0, 0.0, "", "", "", 0,
        emptyList()
    ),
    val clubDetails: ClubDetails = ClubDetails(0, "", "", "", "", "", "", "", "", "", false, "", emptyList(), emptyList()),
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL
)
