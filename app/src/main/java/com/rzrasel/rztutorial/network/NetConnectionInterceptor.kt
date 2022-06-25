package com.rzrasel.rztutorial.network

import android.content.Context
import android.net.ConnectivityManager
import com.rzrasel.rztutorial.util.InternetException
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception

class NetConnectionInterceptor(context: Context): Interceptor {
    private val context = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isAvailable()) {
            throw InternetException("Make sure your internet connection is available")
        }
        return chain.proceed(chain.request())
    }

    private fun isAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}