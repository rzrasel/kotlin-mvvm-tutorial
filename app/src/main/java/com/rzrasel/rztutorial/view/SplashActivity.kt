package com.rzrasel.rztutorial.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.rzrasel.rztutorial.R
import com.rzrasel.rztutorial.databinding.ActivitySplashBinding
import com.rzrasel.rztutorial.datastore.UserPreferences
import com.rzrasel.rztutorial.viewmodel.SplashActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private var isTimeOver = false
    private var isDataReadFinished = false
    private var isDataExists = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )

        val viewModel = ViewModelProvider(this)[SplashActivityViewModel::class.java]
        viewModel.isFinished.observe(this, Observer { isFinished ->
            if (isFinished) {
                isTimeOver = true
                onRedirectIntent()
            }
        })

        val userPreferences = UserPreferences(this)
        lifecycleScope.launch {
            userPreferences.authToken
                .collect {
                    Log.d("DEBUG_LOG_PRINT", "USER_AUTH_TOKEN: $it")
                    isDataReadFinished = true
                    if(it != null) {
                        isDataExists = true
                    }
                    onRedirectIntent()
                }
        }
    }

    private fun onRedirectIntent() {
        if(isTimeOver && isDataReadFinished && isDataExists) {
            onRedirectIntent(DashboardActivity::class.java)
        } else if(isTimeOver && isDataReadFinished && !isDataExists) {
            onRedirectIntent(LoginActivity::class.java)
        }
    }
    private fun onRedirectIntent(clazz: Class<*>?) {
        val intent = Intent(this@SplashActivity, clazz)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finishAffinity()
    }
}