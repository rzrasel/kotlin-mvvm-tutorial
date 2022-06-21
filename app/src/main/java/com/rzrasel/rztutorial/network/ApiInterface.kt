package com.rzrasel.rztutorial.network

import com.rzrasel.rztutorial.model.ProductsItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("products")
    suspend fun getProducts(): Response<List<ProductsItem>>
}