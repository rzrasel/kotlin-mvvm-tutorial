package com.rzrasel.rztutorial.view.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.rzrasel.rztutorial.factory.ViewModelFactory
import com.rzrasel.rztutorial.network.RetrofitClient
import com.rzrasel.rztutorial.repository.BaseRepository

abstract class BaseActivity<VModel: ViewModel, VBinding: ViewBinding, Repo: BaseRepository>: AppCompatActivity() {
    protected lateinit var binding: VBinding
    protected lateinit var viewModel: VModel
    protected val remoteDataSource = RetrofitClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        val factory = ViewModelFactory(getActivityRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())
        onCreated(savedInstanceState)
    }

    abstract fun getViewModel(): Class<VModel>
    abstract fun getViewBinding(): VBinding
    abstract fun getActivityRepository(): Repo

    abstract fun onCreated(savedInstanceState: Bundle?)
}