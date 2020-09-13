package com.anggitprayogo.movon.feature.favouritedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anggitprayogo.movon.data.local.entity.MovieEntity
import com.anggitprayogo.movon.domain.MovieUseCase
import com.eoa.tech.core.base.BaseViewModel
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
interface FavouriteDetailContract {
    fun getMovieDetailDb(movieId: String)
    fun insertMovieToDb(movieEntity: MovieEntity)
    fun deleteMovieFromDb(movieEntity: MovieEntity)
}

class FavouriteDetailViewModel @Inject constructor(
    private val useCase: MovieUseCase,
    dispatcher: SchedulerProvider
) : BaseViewModel(dispatcher), FavouriteDetailContract {

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


    override fun getMovieDetailDb(movieId: String) {
        viewModelScope.launch {
            when (val result = useCase.getMovieDetailByMovieId(movieId.toInt())) {
                is ResultState.Success -> _resultDetailFromDb.postValue(result.data)
                is ResultState.Error -> _error.postValue(result.error)
            }
        }
    }

    override fun insertMovieToDb(movieEntity: MovieEntity) {
        viewModelScope.launch {
            try {
                useCase.insertMovieToDb(movieEntity)
                withContext(Dispatchers.Main) {
                    _resultInsertMovieToDb.postValue(true)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.postValue(e.localizedMessage)
                }
            }
        }
    }

    override fun deleteMovieFromDb(movieEntity: MovieEntity) {
        viewModelScope.launch {
            try {
                useCase.deleteMovieFromDb(movieEntity)
                withContext(Dispatchers.Main) {
                    _resultDeleteMovieFromDb.postValue(true)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.postValue(e.localizedMessage)
                }
            }
        }
    }
}