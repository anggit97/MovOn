package com.anggitprayogo.movon.feature.detail

import android.os.Bundle
import com.anggitprayogo.movon.BaseApplication
import com.anggitprayogo.movon.databinding.ActivityMovieDetailBinding
import com.eoa.tech.core.base.BaseActivity

class MovieDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        initInjector()
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initInjector() {
        (application as BaseApplication).appComponent.inject(this)
    }
}