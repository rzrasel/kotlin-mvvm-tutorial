package com.rzrasel.rztutorial.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("token")
    val accessToken: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any
)
