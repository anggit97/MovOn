package com.anggitprayogo.movon.feature.favouritedetail

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.anggitprayogo.movon.BaseApplication
import com.anggitprayogo.movon.R
import com.anggitprayogo.movon.data.local.entity.MovieEntity
import com.anggitprayogo.movon.data.local.entity.ShareEntity
import com.anggitprayogo.movon.databinding.ActivityFavouriteDetailBinding
import com.anggitprayogo.movon.feature.detail.MovieDetailActivity.Companion.MOVIE_ID_KEY
import com.anggitprayogo.movon.feature.share.ShareDialogBottomSheetFragment
import com.eoa.tech.core.base.BaseActivity
import com.eoa.tech.core.util.ext.load
import com.eoa.tech.core.util.ext.toast
import javax.inject.Inject

class FavouriteDetailActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FavouriteDetailViewModel

    private var movieId: String? = null
    private var movieEntity: MovieEntity? = null

    private var favouriteActive = false

    private lateinit var binding: ActivityFavouriteDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        initInjector()
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleIntent()
        initViewModel()
        fetchData()
        observeViewModel()
        initListner()
        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Detail"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initListner() {
        with(binding) {
            ivFavourite.setOnClickListener {
                if (favouriteActive) {
                    movieEntity?.let { it1 -> viewModel.deleteMovieFromDb(it1) }
                } else {
                    movieEntity?.let { it1 -> viewModel.insertMovieToDb(it1) }
                }
            }

            ivShare.setOnClickListener {
                showShareBottomSheet()
            }
        }
    }

    private fun showShareBottomSheet() {
        val bundle = Bundle()
        val movieShareEntity = ShareEntity(
            movieEntity?.title,
            movieEntity?.overview
        )
        bundle.putParcelable(ShareDialogBottomSheetFragment.MovieData, movieShareEntity)

        val bottomSheet = ShareDialogBottomSheetFragment().newInstance()
        bottomSheet?.arguments = bundle
        bottomSheet?.show(supportFragmentManager, ShareDialogBottomSheetFragment.TAG)
    }

    private fun handleIntent() {
        movieId = intent.getStringExtra(MOVIE_ID_KEY)
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory)[FavouriteDetailViewModel::class.java]
    }

    private fun fetchData() {
        movieId?.let {
            viewModel.getMovieDetailDb(it)
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            resultDetailFromDb.observe(this@FavouriteDetailActivity, {
                handleStateMovieDetailFromDb(it)
            })

            resultInsertMovieToDb.observe(this@FavouriteDetailActivity, {
                if (it) {
                    movieId?.let { viewModel.getMovieDetailDb(it) }
                    toast(getString(R.string.message_success_add_movie_from_favourite))
                }
            })

            resultDeleteMovieFromDb.observe(this@FavouriteDetailActivity, {
                if (it) {
                    movieId?.let { viewModel.getMovieDetailDb(it) }
                    toast(getString(R.string.message_success_remove_movie_from_favourite))
                }
            })

            error.observe(this@FavouriteDetailActivity, {
                toast(it)
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
            bindDataToView()
        }
    }

    private fun bindDataToView() {
        with(binding) {
            movieEntity?.let { movie ->
                ivBannerMovie.load(movie.bannerUrl ?: "")
                tvMovieMetaData.text = movie.genres
                tvMovieTitle.text = movie.title
                tvImdbRating.text = getString(
                    R.string.imdb_rating_template,
                    (movie.vote?.toFloat() ?: 0.0).toString()
                )
                ratingMovie.rating = movie.vote?.toFloat() ?: 0f
                tvOverview.text = movie.overview
            }
        }
    }

    override fun initInjector() {
        (application as BaseApplication).appComponent.inject(this)
    }
}