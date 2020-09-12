package com.anggitprayogo.movon.feature.main

import android.os.Bundle
import com.anggitprayogo.movon.BaseApplication
import com.anggitprayogo.movon.databinding.ActivityMainBinding
import com.eoa.tech.core.base.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        initInjector()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initInjector() {
        (application as BaseApplication).appComponent.inject(this)
    }
}