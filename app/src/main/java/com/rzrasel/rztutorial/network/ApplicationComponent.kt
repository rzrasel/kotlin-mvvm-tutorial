package com.rzrasel.rztutorial.network

import com.rzrasel.rztutorial.application.AppApplication
import com.rzrasel.rztutorial.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiClient::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
}