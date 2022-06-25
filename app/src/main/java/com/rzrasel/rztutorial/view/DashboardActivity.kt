package com.rzrasel.rztutorial.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.rzrasel.rztutorial.databinding.ActivityDashboardBinding
import com.rzrasel.rztutorial.datastore.UserPreferences
import com.rzrasel.rztutorial.model.User
import com.rzrasel.rztutorial.network.NetConnectionInterceptor
import com.rzrasel.rztutorial.network.Resource
import com.rzrasel.rztutorial.network.RetrofitApi
import com.rzrasel.rztutorial.repository.UserRepository
import com.rzrasel.rztutorial.view.base.BaseActivity
import com.rzrasel.rztutorial.viewmodel.DashboardViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DashboardActivity : BaseActivity<DashboardViewModel, ActivityDashboardBinding, UserRepository>() {

    override fun getViewModel() = DashboardViewModel::class.java

    override fun getViewBinding() = ActivityDashboardBinding.inflate(layoutInflater)

    override fun getActivityRepository(): UserRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        Log.d("DEBUG_LOG_PRINT", "onCreated: token $token")
        val api = remoteDataSource.buildApi(RetrofitApi::class.java, NetConnectionInterceptor(this), token)
        return UserRepository(api)
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        /*super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)*/

        val userPreferences = UserPreferences(this)
        binding.btnLogout.setOnClickListener {
            lifecycleScope.launch {
                userPreferences.removeAuthToken()
                val intent = Intent(this@DashboardActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }

        viewModel.getUser()
        viewModel.userData.observe(this, Observer {
            when(it) {
                is Resource.Success -> {
                    onUpdateUi(it.value.user)
                }
                is Resource.Failure -> {
                    Log.d("DEBUG_LOG_PRINT", "onCreated: failure ${it.toString()}")
                }
            }
        })
    }

    private fun onUpdateUi(user: User) {
        binding.textViewUserData.text = "Name: ${user.name}\nEmail: ${user.email}"
    }
}