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
import com.eoa.tech.core.util.ext.setGone
import com.eoa.tech.core.util.ext.setInvisible
import com.eoa.tech.core.util.ext.setVisible
import com.eoa.tech.core.util.state.LoaderState
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
        onActionSwipeListener()
    }

    private fun onActionSwipeListener() {
        with(binding) {
            swipeRefreshLayout.setOnRefreshListener {
                swipeRefreshLayout.isRefreshing = false
                fetchData()
            }
        }
    }

    private fun fetchData() {
        with(viewModel) {
            getPopularMovies()
            getTopRatedMovies()
            getNowPlayingMovies()
        }
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

            loadingNowPlayingMovies.observe(this@MainActivity, {
                it?.let { loading -> handleLoadingNowPlayingMovie(loading) }
            })
        }
    }

    private fun handleLoadingNowPlayingMovie(loading: LoaderState) {
        with(binding) {
            if (loading is LoaderState.ShowLoading) {
                rvNowPlayingMovie.setInvisible()
                loadingNowPlayingMovie.root.setVisible()
            } else {
                rvNowPlayingMovie.setVisible()
                loadingNowPlayingMovie.root.setInvisible()
            }
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

            loadingTopRatedMovies.observe(this@MainActivity, {
                it?.let { loading -> handleLoadingTopRatedMovie(loading) }
            })
        }
    }

    private fun handleLoadingTopRatedMovie(loading: LoaderState) {
        with(binding) {
            if (loading is LoaderState.ShowLoading) {
                rvTopRatedMovie.setInvisible()
                loadingTopRatedMovie.root.setVisible()
            } else {
                rvTopRatedMovie.setVisible()
                loadingTopRatedMovie.root.setInvisible()
            }
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

            loadingPopularMovie.observe(this@MainActivity, {
                it?.let { loading -> handleLoadingPopularMovie(loading) }
            })
        }
    }

    private fun handleLoadingPopularMovie(loading: LoaderState) {
        with(binding) {
            if (loading is LoaderState.ShowLoading) {
                rvPopularMovie.setInvisible()
                loadingPopularMovie.root.setVisible()
            } else {
                rvPopularMovie.setVisible()
                loadingPopularMovie.root.setInvisible()
            }
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