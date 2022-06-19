package com.rzrasel.rztutorial.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rzrasel.rztutorial.repository.AuthRepository
import com.rzrasel.rztutorial.repository.BaseRepository
import com.rzrasel.rztutorial.viewmodel.AuthViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: BaseRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass not found")
        }
    }
}