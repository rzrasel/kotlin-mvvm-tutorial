package com.rzrasel.rztutorial.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzrasel.rztutorial.model.LoginResponse
import com.rzrasel.rztutorial.network.Resource
import com.rzrasel.rztutorial.repository.RegistrationRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

class RegistrationViewModel(private val repository: RegistrationRepository) : ViewModel() {
    private val _registrationResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val registrationResponse: LiveData<Resource<LoginResponse>>
        get() = _registrationResponse

    fun registration(name: String, email: String, password: String) = viewModelScope.launch {
        //_registrationResponse.value = repository.registration(name, email, password)
        val response = repository.registration(name, email, password) as Resource<LoginResponse>
        when(response) {
            is Resource.Success -> {
                if(response.value.isError) {
                    val errorResponse = "{\"type\":\"error\",\"message\":\"${response.value.message}\"}"
                    _registrationResponse.value = Resource.Failure(false, null, errorResponse.toResponseBody("application/json".toMediaTypeOrNull()))
                } else {
                    _registrationResponse.value = response
                }
            }
            is Resource.Failure -> {
                _registrationResponse.value = response
            }
        }
    }
}