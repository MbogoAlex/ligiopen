package com.jabulani.ligiopen.data.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserRegistrationRequestBody(
    val username: String,
    val email: String,
    val password: String,
)
