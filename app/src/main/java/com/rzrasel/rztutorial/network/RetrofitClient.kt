package com.rzrasel.rztutorial.network

import com.rzrasel.rztutorial.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        private const val BASE_URL = "https://rzrasel.000webhostapp.com/plugins/"
        private const val BASE_UORL = "http://rzrasel.com/rzrasel/rztutorial/"
        private const val BASIE_URL = "http://rzrasel.com/rzrasel/rztutorial/"
    }
    fun <Api> buildApi(api: Class<Api>, netConnectionInterceptor: NetConnectionInterceptor, authToken: String? = null): Api {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(netConnectionInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        chain.proceed(chain.request().newBuilder().also {
                            it.addHeader("Authorization", "Bearer $authToken")
                        }.build())
                    }.also { client ->
                    if(BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }
                }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}