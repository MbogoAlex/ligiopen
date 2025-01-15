package com.jabulani.ligiopen.ui.inapp.clubs

import com.jabulani.ligiopen.data.db.model.UserAccount
import com.jabulani.ligiopen.data.network.model.club.ClubDetails

data class ClubDetailsScreenUiData(
    val userAccount: UserAccount = UserAccount(0, "", "", "", "", "", ""),
    val clubDetails: ClubDetails = ClubDetails(0, "", "", "", "", "", "", "", "", "", false, "", emptyList(), emptyList()),
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL
)
