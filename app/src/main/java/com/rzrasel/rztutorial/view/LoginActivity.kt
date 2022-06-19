package com.rzrasel.rztutorial.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.rzrasel.rztutorial.R
import com.rzrasel.rztutorial.databinding.ActivityLoginBinding
import com.rzrasel.rztutorial.network.Resource
import com.rzrasel.rztutorial.network.RetrofitApi
import com.rzrasel.rztutorial.repository.AuthRepository
import com.rzrasel.rztutorial.view.base.BaseActivity
import com.rzrasel.rztutorial.viewmodel.AuthViewModel

class LoginActivity : BaseActivity<AuthViewModel, ActivityLoginBinding, AuthRepository>() {
    override fun getViewModel() = AuthViewModel::class.java

    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun getActivityRepository() = AuthRepository(
        remoteDataSource.buildApi(RetrofitApi::class.java)
    )

    override fun onCreated(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            when(it) {
                is Resource.Success -> {
                    Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG).show()
                } is Resource.Failure -> {
                    Toast.makeText(applicationContext, "Login failure", Toast.LENGTH_LONG).show()
                }
            }
        })
        // Registration text click listener
        binding.txtRegistration.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}