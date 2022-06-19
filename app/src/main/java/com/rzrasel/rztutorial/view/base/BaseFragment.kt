package com.rzrasel.rztutorial.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.rzrasel.rztutorial.factory.ViewModelFactory
import com.rzrasel.rztutorial.network.RetrofitClient
import com.rzrasel.rztutorial.repository.BaseRepository

abstract class BaseFragment<VModel: ViewModel, VBinding: ViewBinding, Repo: BaseRepository>: Fragment() {
    protected lateinit var binding: VBinding
    protected lateinit var viewModel: VModel
    protected val remoteDataSource = RetrofitClient()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(inflater, container)
        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())
        return binding.root
    }

    abstract fun getViewModel(): Class<VModel>
    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VBinding
    abstract fun getFragmentRepository(): Repo
}