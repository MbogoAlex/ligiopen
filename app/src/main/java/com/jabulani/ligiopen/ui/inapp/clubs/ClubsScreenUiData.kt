package com.jabulani.ligiopen.ui.inapp.clubs

import com.jabulani.ligiopen.data.db.model.UserAccount
import com.jabulani.ligiopen.data.db.model.variables.userAccountDt
import com.jabulani.ligiopen.data.network.model.club.ClubDetails

data class ClubsScreenUiData(
    val userAccount: UserAccount = userAccountDt,
    val clubs: List<ClubDetails> = emptyList(),
    val loadingStatus: LoadingStatus = LoadingStatus.LOADING,
)
