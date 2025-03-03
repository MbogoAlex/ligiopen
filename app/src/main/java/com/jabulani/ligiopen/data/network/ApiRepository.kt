package com.jabulani.ligiopen.data.network

import com.admin.ligiopen.data.network.models.match.location.MatchLocationResponseBody
import com.admin.ligiopen.data.network.models.match.location.MatchLocationsResponseBody
import com.jabulani.ligiopen.data.network.model.club.ClubResponseBody
import com.jabulani.ligiopen.data.network.model.club.ClubsResponseBody
import com.jabulani.ligiopen.data.network.model.club.PlayerResponseBody
import com.jabulani.ligiopen.data.network.model.match.commentary.FullMatchResponseBody
import com.jabulani.ligiopen.data.network.model.match.commentary.MatchCommentaryResponseBody
import com.jabulani.ligiopen.data.network.model.match.fixture.FixtureResponseBody
import com.jabulani.ligiopen.data.network.model.match.fixture.FixturesResponseBody
import com.jabulani.ligiopen.data.network.model.user.UserLoginRequestBody
import com.jabulani.ligiopen.data.network.model.user.UserLoginResponseBody
import com.jabulani.ligiopen.data.network.model.user.UserRegistrationRequestBody
import com.jabulani.ligiopen.data.network.model.user.UserRegistrationResponseBody
import retrofit2.Response

interface ApiRepository {
    suspend fun createUserAccount(userRegistrationRequestBody: UserRegistrationRequestBody): Response<UserRegistrationResponseBody>
    suspend fun login(userLoginRequestBody: UserLoginRequestBody): Response<UserLoginResponseBody>

    suspend fun getClubs(
        token: String
    ): Response<ClubsResponseBody>


    suspend fun getClubById(
        token: String,
        id: Int
    ): Response<ClubResponseBody>


    suspend fun getPlayerById(
        token: String,
        id: Int
    ): Response<PlayerResponseBody>

    //    Get match location
    suspend fun getMatchLocation(
        token: String,
        locationId: Int,
    ): Response<MatchLocationResponseBody>

    //    Get match locations
    suspend fun getMatchLocations(
        token: String,
    ): Response<MatchLocationsResponseBody>

    //    Get match fixture
    suspend fun getMatchFixture(
        token: String,
        fixtureId: Int,
    ): Response<FixtureResponseBody>

    //    Get match fixtures
    suspend fun getMatchFixtures(
        token: String,
        status: String?,
        clubId: Int?
    ): Response<FixturesResponseBody>

    //    Get match commentary
    suspend fun getMatchCommentary(
        token: String,
        commentaryId: Int,
    ): Response<MatchCommentaryResponseBody>

    //    Get full match details
    suspend fun getFullMatchDetails(
        token: String,
        postMatchAnalysisId: Int,
    ): Response<FullMatchResponseBody>


    //    Get club
    suspend fun getClub(
        token: String,
        clubId: Int,
    ): Response<ClubResponseBody>

    //    Get player
    suspend fun getPlayer(
        token: String,
        playerId: Int,
    ): Response<PlayerResponseBody>
}