package com.anggitprayogo.movon.feature.favourite

import android.os.Bundle
import com.anggitprayogo.movon.BaseApplication
import com.anggitprayogo.movon.databinding.ActivityFavouriteBinding
import com.eoa.tech.core.base.BaseActivity

class FavouriteActivity : BaseActivity() {

    private lateinit var binding: ActivityFavouriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        initInjector()
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initInjector() {
        (application as BaseApplication).appComponent.inject(this)
    }
}