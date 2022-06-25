package com.rzrasel.rztutorial.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzrasel.rztutorial.model.LoginResponse
import com.rzrasel.rztutorial.network.Resource
import com.rzrasel.rztutorial.repository.AuthRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    fun login(email: String, password: String) = viewModelScope.launch {
        //_loginResponse.value = repository.login(email, password)

        val response = repository.login(email, password) as Resource<LoginResponse>
        when(response) {
            is Resource.Success -> {
                if(response.value.isError) {
                    val errorResponse = "{\"type\":\"error\",\"message\":\"${response.value.message}\"}"
                    _loginResponse.value = Resource.Failure(false, null, errorResponse.toResponseBody("application/json".toMediaTypeOrNull()))
                } else {
                    _loginResponse.value = response
                }
            }
            is Resource.Failure -> {
                _loginResponse.value = response
            }
        }
    }

    fun saveAuthToken(token: String) = viewModelScope.launch {
        repository.saveAuthToken(token)
    }
}