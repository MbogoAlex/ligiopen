package com.jabulani.ligiopen.data.network

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

interface ApiService {
    @POST("auth/register")
    suspend fun createUserAccount(
       @Body userRegistrationRequestBody: UserRegistrationRequestBody
    ): Response<UserRegistrationResponseBody>

    @POST("auth/login")
    suspend fun login(
        @Body userLoginRequestBody: UserLoginRequestBody
    ): Response<UserLoginResponseBody>

    @GET("club")
    suspend fun getClubs(
        @Header("Authorization") token: String
    ): Response<ClubsResponseBody>

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


}