package com.rzrasel.rztutorial.repository

import com.rzrasel.rztutorial.network.RetrofitApi

class AuthRepository(private val api: RetrofitApi): BaseRepository() {
    suspend fun login(email: String, password: String) = retrofitApiCall {
        api.login(email, password)
    }
}