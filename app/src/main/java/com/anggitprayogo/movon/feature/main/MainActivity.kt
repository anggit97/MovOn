package com.anggitprayogo.movon.feature.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggitprayogo.movon.BaseApplication
import com.anggitprayogo.movon.data.remote.MoviesResponse
import com.anggitprayogo.movon.databinding.ActivityMainBinding
import com.anggitprayogo.movon.feature.main.adapter.NowPlayingMovieAdapter
import com.anggitprayogo.movon.feature.main.adapter.PopularMovieAdapter
import com.anggitprayogo.movon.feature.main.adapter.TopRatedMovieAdapter
import com.eoa.tech.core.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel

    private val popularMovieAdapter: PopularMovieAdapter by lazy { PopularMovieAdapter() }
    private val topRatedMovieAdapter: TopRatedMovieAdapter by lazy { TopRatedMovieAdapter() }
    private val nowPlayingMovieAdapter: NowPlayingMovieAdapter by lazy { NowPlayingMovieAdapter() }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        initInjector()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        initRecyclerViewPopularMovie()
        initRecyclerviewTopRatedMovie()
        initRecyclerViewNowPlayingMovie()
    }

    private fun initRecyclerViewNowPlayingMovie() {
        binding.rvNowPlayingMovie.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = nowPlayingMovieAdapter
        }
    }

    private fun initRecyclerviewTopRatedMovie() {
        binding.rvTopRatedMovie.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = topRatedMovieAdapter
        }
    }

    private fun initRecyclerViewPopularMovie() {
        binding.rvPopularMovie.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularMovieAdapter
        }
    }

    private fun observeViewModel() {
        observePopularMovie()
        observeTopRatedMovie()
        observeNowPlayingMovies()
    }

    private fun observeNowPlayingMovies() {
        with(viewModel) {
            nowPlayingMovies.observe(this@MainActivity, {
                it?.let { nowPlayingMovies -> showNowPlayingMovies(nowPlayingMovies) }
            })
        }
    }

    private fun showNowPlayingMovies(nowPlayingMovies: MoviesResponse) {
        nowPlayingMovieAdapter.setItems(nowPlayingMovies.movies.toMutableList())
    }

    private fun observeTopRatedMovie() {
        with(viewModel) {
            topRatedMovies.observe(this@MainActivity, {
                it?.let { topRatedMovies -> showTopRatedMovies(topRatedMovies) }
            })
        }
    }

    private fun showTopRatedMovies(topRatedMovies: MoviesResponse) {
        topRatedMovieAdapter.setItems(topRatedMovies.movies.toMutableList())
    }

    private fun observePopularMovie() {
        with(viewModel) {
            popularMovies.observe(this@MainActivity, {
                it?.let { popularMovies -> showPopularMovie(popularMovies) }
            })
        }
    }

    private fun showPopularMovie(popularMovies: MoviesResponse) {
        popularMovieAdapter.setItems(popularMovies.movies.toMutableList())
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun initInjector() {
        (application as BaseApplication).appComponent.inject(this)
    }
}