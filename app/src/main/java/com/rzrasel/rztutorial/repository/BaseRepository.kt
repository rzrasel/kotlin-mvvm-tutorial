package com.rzrasel.rztutorial.repository

import com.rzrasel.rztutorial.model.LoginResponse
import com.rzrasel.rztutorial.network.Resource
import com.rzrasel.rztutorial.util.InternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException

abstract class BaseRepository {
    suspend fun <T> retrofitApiCall(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when(throwable) {
                    is HttpException -> {
                        Resource.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    is InternetException -> {
                        Resource.Failure(false, null, throwable.message?.toResponseBody())
                    } else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }
}