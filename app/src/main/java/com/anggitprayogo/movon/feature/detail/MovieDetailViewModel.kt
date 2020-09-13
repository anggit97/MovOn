package com.anggitprayogo.movon.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anggitprayogo.movon.data.local.entity.MovieEntity
import com.anggitprayogo.movon.data.remote.MovieDetail
import com.anggitprayogo.movon.data.remote.MovieReviews
import com.anggitprayogo.movon.domain.MovieUseCase
import com.eoa.tech.core.base.BaseViewModel
import com.eoa.tech.core.util.state.LoaderState
import com.eoa.tech.core.util.state.ResultState
import com.eoa.tech.core.util.thread.SchedulerProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Created by Anggit Prayogo on 13,September,2020
 * GitHub : https://github.com/anggit97
 */
interface MovieDetailViewModelContract {
    fun getMovieDetail(movieId: String)
//    fun getMovieDetailDb(movieId: String)
    fun getReviewsByMovieId(movieId: String)
//    fun insertMovieToDb(movieEntity: MovieEntity)
//    fun deleteMovieFromDb(movieEntity: MovieEntity)
}

class MovieDetailViewModel @Inject constructor(
    private val useCase: MovieUseCase,
    dispatcher: SchedulerProvider
) : BaseViewModel(dispatcher), MovieDetailViewModelContract {

    /**
     * Loader State
     */
    private val _state = MutableLiveData<LoaderState>()
    val state: LiveData<LoaderState>
        get() = _state

    /**
     * Detail Movie
     */
    private val _resultDetailMovie = MutableLiveData<MovieDetail>()
    val resultDetailMovie: LiveData<MovieDetail>
        get() = _resultDetailMovie

    /**
     * Reviews
     */
    private val _resultReviews = MutableLiveData<MovieReviews>()
    val resultReviews: LiveData<MovieReviews>
        get() = _resultReviews

    /**
     * Insert movie
     */
    private val _resultInsertMovieToDb = MutableLiveData<Boolean>()
    val resultInsertMovieToDb: LiveData<Boolean>
        get() = _resultInsertMovieToDb

    /**
     * Delete Movie
     */
    private val _resultDeleteMovieFromDb = MutableLiveData<Boolean>()
    val resultDeleteMovieFromDb: LiveData<Boolean>
        get() = _resultDeleteMovieFromDb

    /**
     * Movie Detail from db
     */
    private val _resultDetailFromDb = MutableLiveData<List<MovieEntity>>()
    val resultDetailFromDb: LiveData<List<MovieEntity>>
        get() = _resultDetailFromDb

    /**
     * Error
     */
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error


    /**
     * Network Error
     */
    private val _networkError = MutableLiveData<String>()
    val networkError: LiveData<String>
        get() = _networkError

    override fun getMovieDetail(movieId: String) {
        _state.value = LoaderState.ShowLoading
        viewModelScope.launch {
            val result = useCase.getDetailMovie(movieId)
            _state.value = LoaderState.HideLoading
            when (result) {
                is ResultState.Success -> {
                    _resultDetailMovie.value = result.data
                }
                is ResultState.Error -> {
                    _error.value = result.error
                }
                is ResultState.NetworkError -> {
                    _networkError.value = result.error
                }
            }
        }
    }

    override fun getReviewsByMovieId(movieId: String) {
        _state.value = LoaderState.ShowLoading
        launch {
            val result = useCase.getMovieReviewsByMovieId(movieId)
            withContext(Dispatchers.Main) {
                _state.value = LoaderState.HideLoading
                when (result) {
                    is ResultState.Success -> _resultReviews.value = result.data
                    is ResultState.Error -> _error.value = result.error
                    is ResultState.NetworkError -> _networkError.value = result.error
                }
            }
        }
    }

//    override fun getMovieDetailDb(movieId: String) {
//        launch {
//            val result = useCase.getMovieDetailByMovieId(movieId.toInt())
//            withContext(Dispatchers.Main) {
//                when (result) {
//                    is ResultState.Success -> _resultDetailFromDb.postValue(result.data)
//                    is ResultState.Error -> _error.postValue(result.error)
//                }
//            }
//        }
//    }
//
//    override fun insertMovieToDb(movieEntity: MovieEntity) {
//        launch {
//            try {
//                useCase.insertMovieToDb(movieEntity)
//                withContext(Dispatchers.Main) {
//                    _resultInsertMovieToDb.postValue(true)
//                }
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    _error.postValue(e.localizedMessage)
//                }
//            }
//        }
//    }
//
//    override fun deleteMovieFromDb(movieEntity: MovieEntity) {
//        launch {
//            try {
//                useCase.deleteMovieFromDb(movieEntity)
//                withContext(Dispatchers.Main) {
//                    _resultDeleteMovieFromDb.postValue(true)
//                }
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    _error.postValue(e.localizedMessage)
//                }
//            }
//        }
//    }
}