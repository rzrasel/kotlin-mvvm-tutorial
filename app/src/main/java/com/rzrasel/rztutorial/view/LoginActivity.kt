package com.rzrasel.rztutorial.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.rzrasel.rztutorial.databinding.ActivityLoginBinding
import com.rzrasel.rztutorial.network.NetConnectionInterceptor
import com.rzrasel.rztutorial.network.Resource
import com.rzrasel.rztutorial.network.RetrofitApi
import com.rzrasel.rztutorial.repository.AuthRepository
import com.rzrasel.rztutorial.view.base.BaseActivity
import com.rzrasel.rztutorial.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity<AuthViewModel, ActivityLoginBinding, AuthRepository>() {
    override fun getViewModel() = AuthViewModel::class.java

    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun getActivityRepository() = AuthRepository(
        remoteDataSource.buildApi(RetrofitApi::class.java, NetConnectionInterceptor(this)),
        userPreferences
    )

    override fun onCreated(savedInstanceState: Bundle?) {
        //super.onCreate(savedInstanceState)
        // Login button click listener
        binding.btnLogin.setOnClickListener {
            // Login field data
            val email = binding.editTextEmailAddress.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            // Call viewmodel class (AuthViewModel)
            // @TODO need to add input validation
            viewModel.login(email, password)
        }
        // Observe login response
        viewModel.loginResponse.observe(this@LoginActivity, Observer {
            Log.d("DEBUG_LOG", "Debug print: ${it.toString()}")
            when(it) {
                is Resource.Success -> {
                    viewModel.saveAuthToken(it.value.user.accessToken)
                    //Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG).show()
                    val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                is Resource.Failure -> {
                    Toast.makeText(applicationContext, "Login failure ${it.toString()}", Toast.LENGTH_LONG).show()
                }
            }
        })
        // Registration text click listener
        binding.txtRegistration.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}