package com.jabulani.ligiopen.ui.inapp.fixtures

import com.jabulani.ligiopen.data.db.model.UserAccount
import com.jabulani.ligiopen.data.db.model.variables.userAccountDt
import com.jabulani.ligiopen.data.network.model.match.fixture.FixtureData
import com.jabulani.ligiopen.ui.inapp.clubs.LoadingStatus

data class FixturesUiData(
    val userAccount: UserAccount = userAccountDt,
    val fixtures: List<FixtureData> = emptyList(),
    val clubId: Int? = null,
    val unauthorized: Boolean = false,
    val loadingStatus: LoadingStatus = LoadingStatus.LOADING
)
