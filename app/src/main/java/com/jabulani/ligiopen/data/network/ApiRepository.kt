package com.jabulani.ligiopen.data.network

import com.jabulani.ligiopen.data.network.model.club.ClubResponseBody
import com.jabulani.ligiopen.data.network.model.club.ClubsResponseBody
import com.jabulani.ligiopen.data.network.model.club.PlayerResponseBody
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
}