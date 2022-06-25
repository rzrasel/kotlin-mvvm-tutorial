package com.rzrasel.rztutorial.repository

import com.rzrasel.rztutorial.datastore.UserPreferences
import com.rzrasel.rztutorial.network.RetrofitApi

class AuthRepository(private val api: RetrofitApi, private val preferences: UserPreferences): BaseRepository() {
    suspend fun login(email: String, password: String) = retrofitApiCall {
        api.login(email, password)
    }

    suspend fun saveAuthToken(token: String) {
        preferences.saveAuthToken(token)
    }
}