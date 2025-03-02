package com.jabulani.ligiopen.ui.inapp.clubs

import com.jabulani.ligiopen.data.db.model.UserAccount
import com.jabulani.ligiopen.data.db.model.variables.userAccountDt
import com.jabulani.ligiopen.data.network.model.club.ClubDetails
import com.jabulani.ligiopen.data.network.model.club.club

data class ClubDetailsScreenUiData(
    val userAccount: UserAccount = userAccountDt,
    val clubDetails: ClubDetails = club,
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL
)
