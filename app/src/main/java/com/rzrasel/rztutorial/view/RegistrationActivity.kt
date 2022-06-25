package com.rzrasel.rztutorial.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.rzrasel.rztutorial.R
import com.rzrasel.rztutorial.databinding.ActivityRegistrationBinding
import com.rzrasel.rztutorial.network.NetConnectionInterceptor
import com.rzrasel.rztutorial.network.Resource
import com.rzrasel.rztutorial.network.RetrofitApi
import com.rzrasel.rztutorial.repository.RegistrationRepository
import com.rzrasel.rztutorial.view.base.BaseActivity
import com.rzrasel.rztutorial.viewmodel.RegistrationViewModel

class RegistrationActivity : BaseActivity<RegistrationViewModel, ActivityRegistrationBinding, RegistrationRepository>() {
    //private lateinit var binding: ActivityRegistrationBinding
    override fun getViewModel() = RegistrationViewModel::class.java

    override fun getViewBinding() = ActivityRegistrationBinding.inflate(layoutInflater)

    override fun getActivityRepository() = RegistrationRepository(
        remoteDataSource.buildApi(RetrofitApi::class.java, NetConnectionInterceptor(this))
    )

    override fun onCreated(savedInstanceState: Bundle?) {
        //super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        /*binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)*/

        // Registration button click listener
        binding.btnRegistration.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val email = binding.editTextEmailAddress.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            viewModel.registration(name, email, password)
        }

        // Registration response observe
        viewModel.registrationResponse.observe(this, Observer {
            Log.d("DEBUG_LOG_PRINT", "onCreated: ${it.toString()}")
            when(it) {
                is Resource.Success -> {
                    Toast.makeText(this@RegistrationActivity, "Successfully registration completed", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                is Resource.Failure -> {
                    Toast.makeText(this@RegistrationActivity, "Registration failure", Toast.LENGTH_LONG).show()
                }
            }
        })

        binding.txtLogin.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View?) {
                val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        })
    }
}