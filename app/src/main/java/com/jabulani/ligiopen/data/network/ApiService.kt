package com.jabulani.ligiopen.data.network

import com.jabulani.ligiopen.data.network.model.match.commentary.FullMatchResponseBody
import com.jabulani.ligiopen.data.network.model.match.commentary.MatchCommentaryResponseBody
import com.jabulani.ligiopen.data.network.model.match.fixture.FixtureResponseBody
import com.jabulani.ligiopen.data.network.model.match.fixture.FixturesResponseBody
import com.admin.ligiopen.data.network.models.match.location.MatchLocationResponseBody
import com.admin.ligiopen.data.network.models.match.location.MatchLocationsResponseBody
import com.jabulani.ligiopen.data.network.model.club.ClubResponseBody
import com.jabulani.ligiopen.data.network.model.club.ClubsResponseBody
import com.jabulani.ligiopen.data.network.model.club.PlayerResponseBody
import com.jabulani.ligiopen.data.network.model.user.UserLoginRequestBody
import com.jabulani.ligiopen.data.network.model.user.UserLoginResponseBody
import com.jabulani.ligiopen.data.network.model.user.UserRegistrationRequestBody
import com.jabulani.ligiopen.data.network.model.user.UserRegistrationResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("auth/register")
    suspend fun createUserAccount(
       @Body userRegistrationRequestBody: UserRegistrationRequestBody
    ): Response<UserRegistrationResponseBody>

    @POST("auth/login")
    suspend fun login(
        @Body userLoginRequestBody: UserLoginRequestBody
    ): Response<UserLoginResponseBody>

    @GET("club/{id}")
    suspend fun getClubById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<ClubResponseBody>

    @GET("player/{id}")
    suspend fun getPlayerById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<PlayerResponseBody>

    @GET("match-location/{locationId}")
    suspend fun getMatchLocation(
        @Header("Authorization") token: String,
        @Path("locationId") locationId: Int,
    ): Response<MatchLocationResponseBody>

    @GET("match-location/all")
    suspend fun getMatchLocations(
        @Header("Authorization") token: String,
    ): Response<MatchLocationsResponseBody>

    //    Get match fixture
    @GET("match-fixture/{fixtureId}")
    suspend fun getMatchFixture(
        @Header("Authorization") token: String,
        @Path("fixtureId") fixtureId: Int,
    ): Response<FixtureResponseBody>

    //    Get match fixtures
    @GET("match-fixture/all")
    suspend fun getMatchFixtures(
        @Header("Authorization") token: String,
        @Query("status") status: String?,
    ): Response<FixturesResponseBody>

    //    Get match commentary
    @GET("match-commentary/{commentaryId}")
    suspend fun getMatchCommentary(
        @Header("Authorization") token: String,
        @Path("commentaryId") commentaryId: Int,
    ): Response<MatchCommentaryResponseBody>

    //    Get full match details
    @GET("post-match-details/{postMatchAnalysisId}")
    suspend fun getFullMatchDetails(
        @Header("Authorization") token: String,
        @Path("postMatchAnalysisId") postMatchAnalysisId: Int,
    ): Response<FullMatchResponseBody>

    //    Get clubs
    @GET("club")
    suspend fun getClubs(
        @Header("Authorization") token: String
    ): Response<ClubsResponseBody>

    //    Get club
    @GET("club/{clubId}")
    suspend fun getClub(
        @Header("Authorization") token: String,
        @Path("clubId") clubId: Int,
    ): Response<ClubResponseBody>

    //    Get player
    @GET("player/{playerId}")
    suspend fun getPlayer(
        @Header("Authorization") token: String,
        @Path("playerId") playerId: Int,
    ): Response<PlayerResponseBody>


}