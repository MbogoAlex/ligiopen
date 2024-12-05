package com.jabulani.ligiopen.data.network

import com.jabulani.ligiopen.data.network.model.user.UserLoginRequestBody
import com.jabulani.ligiopen.data.network.model.user.UserLoginResponseBody
import com.jabulani.ligiopen.data.network.model.user.UserRegistrationRequestBody
import com.jabulani.ligiopen.data.network.model.user.UserRegistrationResponseBody

class ApiRepositoryImpl(apiService: ApiService): ApiRepository {
    override suspend fun createUserAccount(userRegistrationRequestBody: UserRegistrationRequestBody): UserRegistrationResponseBody {
        TODO("Not yet implemented")
    }

    override suspend fun login(userLoginRequestBody: UserLoginRequestBody): UserLoginResponseBody {
        TODO("Not yet implemented")
    }
}