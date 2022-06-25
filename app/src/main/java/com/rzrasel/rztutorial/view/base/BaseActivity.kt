package com.rzrasel.rztutorial.view.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.rzrasel.rztutorial.datastore.UserPreferences
import com.rzrasel.rztutorial.factory.ViewModelFactory
import com.rzrasel.rztutorial.network.RetrofitClient
import com.rzrasel.rztutorial.repository.BaseRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class BaseActivity<VModel : ViewModel, VBinding : ViewBinding, Repo : BaseRepository> :
    AppCompatActivity() {
    protected lateinit var binding: VBinding
    protected lateinit var viewModel: VModel
    protected val remoteDataSource = RetrofitClient()
    protected lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        userPreferences = UserPreferences(this)
        val factory = ViewModelFactory(getActivityRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())

        lifecycleScope.launch { userPreferences.authToken.first() }
        onCreated(savedInstanceState)
    }

    abstract fun getViewModel(): Class<VModel>
    abstract fun getViewBinding(): VBinding
    abstract fun getActivityRepository(): Repo

    abstract fun onCreated(savedInstanceState: Bundle?)
}