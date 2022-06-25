package com.rzrasel.rztutorial.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("is_error")
    val isError: Boolean,
    @SerializedName("user_data")
    val user: User
)
