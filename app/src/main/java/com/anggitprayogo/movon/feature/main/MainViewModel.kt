package com.anggitprayogo.movon.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anggitprayogo.movon.data.remote.MoviesResponse
import com.anggitprayogo.movon.domain.MovieUseCase
import com.eoa.tech.core.base.BaseViewModel
import com.eoa.tech.core.util.state.LoaderState
import com.eoa.tech.core.util.state.ResultState
import com.eoa.tech.core.util.thread.SchedulerProvider
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Anggit Prayogo on 12,September,2020
 * GitHub : https://github.com/anggit97
 */
interface MainViewModelContract {

    fun getPopularMovies()

    fun getTopRatedMovies()

    fun getNowPlayingMovies()
}

class MainViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    dispatcher: SchedulerProvider
) : BaseViewModel(dispatcher), MainViewModelContract {

    /**
     * Popular Movies
     */
    private val _loadingPopularMovie = MutableLiveData<LoaderState>()
    val loadingPopularMovie: LiveData<LoaderState>
        get() = _loadingPopularMovie

    private val _errorPopularMovie = MutableLiveData<String>()
    val errorPopularMovie: LiveData<String>
        get() = _errorPopularMovie

    private val _networkPopularMovie = MutableLiveData<String>()
    val networkPopularMovie: LiveData<String>
        get() = _networkPopularMovie

    private val _popularMovies = MutableLiveData<MoviesResponse>()
    val popularMovies: LiveData<MoviesResponse>
        get() = _popularMovies


    /**
     * Top Rated Movies
     */
    private val _loadingTopRatedMovies = MutableLiveData<LoaderState>()
    val loadingTopRatedMovies: LiveData<LoaderState>
        get() = _loadingTopRatedMovies

    private val _errorTopRatedMovies = MutableLiveData<String>()
    val errorTopRatedMovies: LiveData<String>
        get() = _errorTopRatedMovies

    private val _networkTopRatedMovies = MutableLiveData<String>()
    val networkTopRatedMovies: LiveData<String>
        get() = _networkTopRatedMovies

    private val _topRatedMovies = MutableLiveData<MoviesResponse>()
    val topRatedMovies: LiveData<MoviesResponse>
        get() = _topRatedMovies


    /**
     * Now Playing Movies
     */
    private val _loadingNowPlayingMovies = MutableLiveData<LoaderState>()
    val loadingNowPlayingMovies: LiveData<LoaderState>
        get() = _loadingNowPlayingMovies

    private val _errorNowPlayingMovies = MutableLiveData<String>()
    val errorNowPlayingMovies: LiveData<String>
        get() = _errorNowPlayingMovies

    private val _networkNowPlayingMovies = MutableLiveData<String>()
    val networkNowPlayingMovies: LiveData<String>
        get() = _networkNowPlayingMovies

    private val _nowPlayingMovies = MutableLiveData<MoviesResponse>()
    val nowPlayingMovies: LiveData<MoviesResponse>
        get() = _nowPlayingMovies


    override fun getPopularMovies() {
        _loadingPopularMovie.value = LoaderState.ShowLoading
        viewModelScope.launch {
            val result = movieUseCase.getPopularMovie()
            _loadingPopularMovie.value = LoaderState.HideLoading
            when (result) {
                is ResultState.Success -> _popularMovies.value = result.data
                is ResultState.Error -> _errorPopularMovie.value = result.error
                is ResultState.NetworkError -> _networkPopularMovie.value = result.error
            }
        }
    }

    override fun getTopRatedMovies() {
        _loadingTopRatedMovies.value = LoaderState.ShowLoading
        viewModelScope.launch {
            val result = movieUseCase.getTopRatedMovies()
            _loadingTopRatedMovies.value = LoaderState.HideLoading
            when (result) {
                is ResultState.Success -> _topRatedMovies.value = result.data
                is ResultState.Error -> _errorNowPlayingMovies.value = result.error
                is ResultState.NetworkError -> _networkNowPlayingMovies.value = result.error
            }
        }
    }

    override fun getNowPlayingMovies() {
        _loadingNowPlayingMovies.value = LoaderState.ShowLoading
        viewModelScope.launch {
            val result = movieUseCase.getNowPlayingMovies()
            _loadingNowPlayingMovies.value = LoaderState.HideLoading
            when (result) {
                is ResultState.Success -> _nowPlayingMovies.value = result.data
                is ResultState.Error -> _errorNowPlayingMovies.value = result.error
                is ResultState.NetworkError -> _networkNowPlayingMovies.value = result.error
            }
        }
    }
}