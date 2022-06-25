package com.rzrasel.rztutorial.repository

import com.rzrasel.rztutorial.network.RetrofitApi

class UserRepository(private val api: RetrofitApi): BaseRepository() {
    suspend fun getUser() = retrofitApiCall {
        api.getUser()
    }
}