package com.jabulani.ligiopen.data.network

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
}