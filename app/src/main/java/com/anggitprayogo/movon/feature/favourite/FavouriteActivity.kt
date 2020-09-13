package com.anggitprayogo.movon.feature.favourite

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggitprayogo.movon.BaseApplication
import com.anggitprayogo.movon.data.local.entity.MovieEntity
import com.anggitprayogo.movon.databinding.ActivityFavouriteBinding
import com.anggitprayogo.movon.feature.favourite.adapter.FavouriteListAdapter
import com.eoa.tech.core.base.BaseActivity
import com.eoa.tech.core.util.ext.setGone
import com.eoa.tech.core.util.ext.setVisible
import com.eoa.tech.core.util.ext.toast
import kotlinx.android.synthetic.main.activity_favourite.*
import javax.inject.Inject

class FavouriteActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FavouriteListViewModel

    private val movieList = mutableListOf<MovieEntity>()

    private val adapter: FavouriteListAdapter by lazy {
        FavouriteListAdapter()
    }

    private lateinit var binding: ActivityFavouriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        initInjector()
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        observeViewModel()
        initListener()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        rvMovie.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvMovie.adapter = adapter
        adapter.setActivity(this)
    }

    private fun initListener() {
        ivBack.setOnClickListener {
            finish()
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchAllMovies()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.resultMovies.observe(this, {
            it?.let {
                handleStateResultMovies(it)
            }
        })

        viewModel.error.observe(this, {
            toast(it)
        })
    }

    private fun handleStateResultMovies(movies: List<MovieEntity>) {
        handleEmptyViewVisibility(movies)
        movieList.clear()
        movieList.addAll(movies)
        adapter.setItems(movieList)
    }

    private fun handleEmptyViewVisibility(movies: List<MovieEntity>) {
        if (movies.isEmpty()) {
            rvMovie.setGone()
            viewEmpty.setVisible()
        }else{
            rvMovie.setVisible()
            viewEmpty.setGone()
        }
    }

    private fun initViewModel(){
        viewModel =
            ViewModelProviders.of(this, viewModelFactory)[FavouriteListViewModel::class.java]
    }

    override fun initInjector() {
        (application as BaseApplication).appComponent.inject(this)
    }
}