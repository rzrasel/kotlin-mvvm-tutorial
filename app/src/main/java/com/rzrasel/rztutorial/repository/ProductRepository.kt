package com.rzrasel.rztutorial.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rzrasel.rztutorial.model.ProductsItem
import com.rzrasel.rztutorial.network.ApiInterface
import javax.inject.Inject

class ProductRepository @Inject constructor(private val apiInterface: ApiInterface) {
    private val _products = MutableLiveData<List<ProductsItem>>()
    val products: LiveData<List<ProductsItem>>
        get() = _products

    suspend fun getProducts() {
        val result = apiInterface.getProducts()
        if(result.isSuccessful && result.body() != null) {
            _products.postValue(result.body())
        }
    }
}