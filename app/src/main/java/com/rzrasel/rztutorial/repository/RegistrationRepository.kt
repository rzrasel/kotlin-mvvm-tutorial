package com.rzrasel.rztutorial.repository

import com.rzrasel.rztutorial.network.RetrofitApi

class RegistrationRepository(private val api: RetrofitApi): BaseRepository() {
    suspend fun registration(name: String, email: String, password: String) = retrofitApiCall {
        api.registration(name, email, password)
    }
}