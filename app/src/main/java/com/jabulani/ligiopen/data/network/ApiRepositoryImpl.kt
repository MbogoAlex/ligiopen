package com.jabulani.ligiopen.data.network

import com.jabulani.ligiopen.data.network.model.club.ClubResponseBody
import com.jabulani.ligiopen.data.network.model.club.ClubsResponseBody
import com.jabulani.ligiopen.data.network.model.club.PlayerResponseBody
import com.jabulani.ligiopen.data.network.model.user.UserLoginRequestBody
import com.jabulani.ligiopen.data.network.model.user.UserLoginResponseBody
import com.jabulani.ligiopen.data.network.model.user.UserRegistrationRequestBody
import com.jabulani.ligiopen.data.network.model.user.UserRegistrationResponseBody
import retrofit2.Response

class ApiRepositoryImpl(private val apiService: ApiService): ApiRepository {
    override suspend fun createUserAccount(
        userRegistrationRequestBody: UserRegistrationRequestBody
    ): Response<UserRegistrationResponseBody> = apiService.createUserAccount(userRegistrationRequestBody = userRegistrationRequestBody)

    override suspend fun login(
        userLoginRequestBody: UserLoginRequestBody
    ): Response<UserLoginResponseBody> = apiService.login(userLoginRequestBody = userLoginRequestBody)

    override suspend fun getClubs(token: String): Response<ClubsResponseBody> =
        apiService.getClubs(
            token = "Bearer $token"
        )

    override suspend fun getClubById(token: String, id: Int): Response<ClubResponseBody> =
        apiService.getClubById(
            token = "Bearer $token",
            id = id
        )

    override suspend fun getPlayerById(token: String, id: Int): Response<PlayerResponseBody> =
        apiService.getPlayerById(
            token = "Bearer $token",
            id = id
        )


}