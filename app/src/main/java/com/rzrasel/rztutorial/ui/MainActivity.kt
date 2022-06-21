package com.rzrasel.rztutorial.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rzrasel.rztutorial.R
import com.rzrasel.rztutorial.application.AppApplication
import com.rzrasel.rztutorial.databinding.ActivityMainBinding
import com.rzrasel.rztutorial.factory.MainViewModelFactory
import com.rzrasel.rztutorial.viewmodel.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as AppApplication).applicationComponent.inject(this)
        viewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

        viewModel.productLiveData.observe(this, Observer {
            binding.txtProducts.text = it.joinToString { x -> x.title + "\n\n" }
        })
    }
}