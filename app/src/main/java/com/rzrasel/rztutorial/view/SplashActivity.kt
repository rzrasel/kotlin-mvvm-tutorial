package com.rzrasel.rztutorial.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rzrasel.rztutorial.R
import com.rzrasel.rztutorial.databinding.ActivitySplashBinding
import com.rzrasel.rztutorial.viewmodel.SplashActivityViewModel

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

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
            if(isFinished) {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        })
    }
}