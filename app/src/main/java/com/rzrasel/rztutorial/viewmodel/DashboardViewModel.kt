package com.rzrasel.rztutorial.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzrasel.rztutorial.model.LoginResponse
import com.rzrasel.rztutorial.network.Resource
import com.rzrasel.rztutorial.repository.UserRepository
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: UserRepository) : ViewModel() {
    private val _userData: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val userData: LiveData<Resource<LoginResponse>>
        get() = _userData

    fun getUser() = viewModelScope.launch {
        _userData.value = repository.getUser()
    }
}