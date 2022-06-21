package com.rzrasel.rztutorial.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzrasel.rztutorial.model.ProductsItem
import com.rzrasel.rztutorial.repository.ProductRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ProductRepository): ViewModel() {
    val productLiveData: LiveData<List<ProductsItem>>
    get() = repository.products

    init {
        viewModelScope.launch {
            repository.getProducts()
        }
    }
}