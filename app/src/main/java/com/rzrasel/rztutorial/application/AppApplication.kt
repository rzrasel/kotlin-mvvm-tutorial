package com.rzrasel.rztutorial.application

import android.app.Application
import com.rzrasel.rztutorial.network.ApplicationComponent
import com.rzrasel.rztutorial.network.DaggerApplicationComponent

class AppApplication: Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder().build()
    }
}