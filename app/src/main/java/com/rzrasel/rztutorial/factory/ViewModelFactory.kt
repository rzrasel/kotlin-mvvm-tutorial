package com.rzrasel.rztutorial.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rzrasel.rztutorial.repository.AuthRepository
import com.rzrasel.rztutorial.repository.BaseRepository
import com.rzrasel.rztutorial.repository.RegistrationRepository
import com.rzrasel.rztutorial.repository.UserRepository
import com.rzrasel.rztutorial.viewmodel.AuthViewModel
import com.rzrasel.rztutorial.viewmodel.DashboardViewModel
import com.rzrasel.rztutorial.viewmodel.RegistrationViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: BaseRepository): ViewModelProvider.NewInstanceFactory() {
    //override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(RegistrationViewModel::class.java) -> RegistrationViewModel(repository as RegistrationRepository) as T
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> DashboardViewModel(repository as UserRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass not found")
        }
    }
}