package com.anggitprayogo.movon.feature.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
interface FavouriteListContract {
    fun fetchAllMovies()
}

class FavouriteListViewModel @Inject constructor(
    private val useCase: MovieUseCase,
    dispatcher: SchedulerProvider
) : BaseViewModel(dispatcher), FavouriteListContract {

    /**
     * Movie State
     */
    private val _resultMovies = MutableLiveData<List<MovieEntity>>()
    val resultMovies: LiveData<List<MovieEntity>>
        get() = _resultMovies

    /**
     * Error
     */
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        fetchAllMovies()
    }

    override fun fetchAllMovies() {
        launch {
            val result = useCase.getFavouriteMovie()
            withContext(Dispatchers.Main) {
                when (result) {
                    is ResultState.Success -> _resultMovies.postValue(result.data)
                    is ResultState.Error -> _error.postValue(result.error)
                }
            }
        }
    }
}