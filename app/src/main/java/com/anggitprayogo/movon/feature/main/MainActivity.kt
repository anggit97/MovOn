package com.anggitprayogo.movon.feature.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.anggitprayogo.movon.BaseApplication
import com.anggitprayogo.movon.databinding.ActivityMainBinding
import com.eoa.tech.core.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        initInjector()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        observeViewModel()
    }

    private fun observeViewModel() {
        observePopularMovie()
    }

    private fun observePopularMovie() {
        with(viewModel){

        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun initInjector() {
        (application as BaseApplication).appComponent.inject(this)
    }
}