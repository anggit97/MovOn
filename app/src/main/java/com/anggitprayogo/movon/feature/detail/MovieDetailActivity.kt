package com.anggitprayogo.movon.feature.detail

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggitprayogo.movon.BaseApplication
import com.anggitprayogo.movon.R
import com.anggitprayogo.movon.data.local.entity.MovieEntity
import com.anggitprayogo.movon.data.local.entity.ShareEntity
import com.anggitprayogo.movon.data.remote.MovieDetail
import com.anggitprayogo.movon.data.remote.MovieReviews
import com.anggitprayogo.movon.data.remote.Review
import com.anggitprayogo.movon.databinding.ActivityMovieDetailBinding
import com.anggitprayogo.movon.feature.detail.adapter.ReviewsAdapter
import com.anggitprayogo.movon.feature.share.ShareDialogBottomSheetFragment
import com.eoa.tech.core.base.BaseActivity
import com.eoa.tech.core.util.ext.load
import com.eoa.tech.core.util.ext.setGone
import com.eoa.tech.core.util.ext.setVisible
import com.eoa.tech.core.util.ext.toast
import javax.inject.Inject

class MovieDetailActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MovieDetailViewModel

    private var movieDetail: MovieDetail? = null

    private var movieEntity: MovieEntity? = null

    private var movieId: String? = null

    private var favouriteActive = false

    private val reviewsAdapter: ReviewsAdapter by lazy { ReviewsAdapter() }
    private var reviewsList = mutableListOf<Review>()

    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        initInjector()
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleIntent()
        initViewModel()
        observeViewModel()
        initRecyclerViewReviews()
        initToolbar()
        fetchData()
        onActionClickListener()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Detail"
    }

    private fun onActionClickListener() {
        with(binding) {
            ivFavourite.setOnClickListener {
                setFavourite()
            }

            ivShare.setOnClickListener {
                showShareBottomSheet()
            }

            swipeRefreshLayout.setOnRefreshListener {
                swipeRefreshLayout.isRefreshing = false
                fetchData()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showShareBottomSheet() {
        val bundle = Bundle()
        val movieShareEntity = ShareEntity(
            movieDetail?.title,
            movieDetail?.overview
        )
        bundle.putParcelable(ShareDialogBottomSheetFragment.MovieData, movieShareEntity)

        val bottomSheet = ShareDialogBottomSheetFragment().newInstance()
        bottomSheet?.arguments = bundle
        bottomSheet?.show(supportFragmentManager, ShareDialogBottomSheetFragment.TAG)
    }

    private fun setFavourite() {
        if (favouriteActive) {
            movieEntity?.let { it1 -> viewModel.deleteMovieFromDb(it1) }
        } else {
            val movieEntity = MovieEntity(
                movieId = movieId?.toInt(),
                title = movieDetail?.title,
                genres = movieDetail?.getMetaData(),
                vote = movieDetail?.getImdbRating(),
                releaseDate = movieDetail?.releaseDate,
                overview = movieDetail?.overview,
                bannerUrl = movieDetail?.getBannerMovie(),
                posterUrl = movieDetail?.getPosterMovie()
            )
            viewModel.insertMovieToDb(movieEntity)
        }
    }

    private fun initRecyclerViewReviews() {
        with(binding) {
            rvReviews.adapter = reviewsAdapter
            rvReviews.layoutManager =
                LinearLayoutManager(this@MovieDetailActivity, LinearLayoutManager.VERTICAL, false)
            rvReviews.addItemDecoration(
                DividerItemDecoration(
                    this@MovieDetailActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }


    private fun fetchData() {
        movieId?.let {
            viewModel.getMovieDetail(it)
            viewModel.getReviewsByMovieId(it)
            viewModel.getMovieDetailDb(it)
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            resultDetailMovie.observe(this@MovieDetailActivity, {
                it?.let { handleStateMovieDetail(it) }
            })

            resultReviews.observe(this@MovieDetailActivity, {
                it?.let { handleStateReviews(it) }
            })

            resultDetailFromDb.observe(this@MovieDetailActivity, {
                it?.let { handleStateMovieDetailFromDb(it) }
            })

            resultInsertMovieToDb.observe(this@MovieDetailActivity, {
                if (it) {
                    movieId?.let { viewModel.getMovieDetailDb(it) }
                    toast(getString(R.string.message_success_add_movie_from_favourite))
                }
            })

            resultDeleteMovieFromDb.observe(this@MovieDetailActivity, {
                if (it) {
                    movieId?.let { viewModel.getMovieDetailDb(it) }
                    toast(getString(R.string.message_success_remove_movie_from_favourite))
                }
            })

            networkError.observe(this@MovieDetailActivity, {
                it?.let { toast(it) }
            })

            error.observe(this@MovieDetailActivity, {
                it?.let { toast(it) }
            })
        }
    }

    private fun handleStateMovieDetailFromDb(result: List<MovieEntity>) {
        if (result.isEmpty()) {
            favouriteActive = false
            val icon = R.drawable.ic_baseline_favorite_border_24
            binding.ivFavourite.setImageResource(icon)
        } else {
            movieEntity = result.first()
            favouriteActive = true
            val icon = R.drawable.ic_baseline_favorite_red_24
            binding.ivFavourite.setImageResource(icon)
        }
    }

    private fun handleStateReviews(reviews: MovieReviews) {
        with(binding) {
            handleReviewEmptyState(reviews)
            tvReviewTotal.text = reviews.results.size.toString()
            reviewsList.clear()
            reviewsList.addAll(reviews.results.toMutableList())
            reviewsAdapter.setItems(reviewsList)
        }
    }

    private fun handleReviewEmptyState(reviews: MovieReviews) {
        with(binding) {
            if (reviews.results.isEmpty()) {
                rvReviews.setGone()
                viewEmpty.root.setVisible()
            } else {
                rvReviews.setVisible()
                viewEmpty.root.setGone()
            }
        }
    }

    private fun handleStateMovieDetail(movie: MovieDetail) {
        movieDetail = movie
        with(binding) {
            movieDetail = movie
            ivBannerMovie.load(movie.getBannerMovie())
            tvMovieMetaData.text =
                getString(R.string.genre_release_date, movie.releaseDate, movie.getMetaData())
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