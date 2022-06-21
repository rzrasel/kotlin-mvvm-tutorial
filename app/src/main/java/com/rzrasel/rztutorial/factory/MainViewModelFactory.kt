package com.rzrasel.rztutorial.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rzrasel.rztutorial.repository.ProductRepository
import com.rzrasel.rztutorial.viewmodel.MainViewModel
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val repository: ProductRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}