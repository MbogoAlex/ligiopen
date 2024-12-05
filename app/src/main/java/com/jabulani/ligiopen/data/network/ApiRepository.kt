package com.jabulani.ligiopen.data.network

import com.jabulani.ligiopen.data.network.model.user.UserLoginRequestBody
import com.jabulani.ligiopen.data.network.model.user.UserLoginResponseBody
import com.jabulani.ligiopen.data.network.model.user.UserRegistrationRequestBody
import com.jabulani.ligiopen.data.network.model.user.UserRegistrationResponseBody

interface ApiRepository {
    suspend fun createUserAccount(userRegistrationRequestBody: UserRegistrationRequestBody): UserRegistrationResponseBody
    suspend fun login(userLoginRequestBody: UserLoginRequestBody): UserLoginResponseBody
}