package com.anggitprayogo.movon.feature.detail

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.anggitprayogo.movon.BaseApplication
import com.anggitprayogo.movon.R
import com.anggitprayogo.movon.data.local.entity.MovieEntity
import com.anggitprayogo.movon.data.remote.MovieDetail
import com.anggitprayogo.movon.databinding.ActivityMovieDetailBinding
import com.eoa.tech.core.base.BaseActivity
import com.eoa.tech.core.util.ext.load
import com.eoa.tech.core.util.ext.toast
import javax.inject.Inject

class MovieDetailActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MovieDetailViewModel

    private var movieDetail: MovieDetail? = null

    private var movieEntity: MovieEntity? = null

    private var movieId: String? = null


    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        initInjector()
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleIntent()
        initViewModel()
        observeViewModel()
//        initListener()
        fetchData()
    }

    private fun fetchData() {
        movieId?.let {
            viewModel.getMovieDetail(it)
            viewModel.getReviewsByMovieId(it)
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            resultDetailMovie.observe(this@MovieDetailActivity, {
                it?.let { handleStateMovieDetail(it) }
            })

            viewModel.networkError.observe(this@MovieDetailActivity, {
                it?.let { toast(it) }
            })

            viewModel.error.observe(this@MovieDetailActivity, {
                it?.let { toast(it) }
            })
        }
    }

    private fun handleStateMovieDetail(movie: MovieDetail) {
        movieDetail = movie
        with(binding) {
            movieDetail = movie
            ivBannerMovie.load(movie.getBannerMovie())
            tvMovieMetaData.text = movie.getMetaData()
            tvMovieTitle.text = movie.originalTitle
            tvImdbRating.text = getString(
                R.string.imdb_rating_template,
                (movie.getImdbRating()?.toFloat() ?: 0.0).toString()
            )
            ratingMovie.rating = movie.getImdbRating()?.toFloat() ?: 0f
            tvOverview.text = movie.overview
        }
    }

    private fun handleIntent() {
        movieId = intent.getStringExtra(MOVIE_ID_KEY)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MovieDetailViewModel::class.java]
    }

    override fun initInjector() {
        (application as BaseApplication).appComponent.inject(this)
    }

    companion object {
        const val MOVIE_ID_KEY = "movie_id_key"
    }
}