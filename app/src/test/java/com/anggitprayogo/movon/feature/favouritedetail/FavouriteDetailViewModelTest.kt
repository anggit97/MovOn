package com.anggitprayogo.movon.feature.favouritedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.anggitprayogo.movon.TestDataSource
import com.anggitprayogo.movon.data.local.entity.MovieEntity
import com.anggitprayogo.movon.domain.MovieUseCase
import com.anggitprayogo.movon.getResponseErrorValue
import com.eoa.tech.core.util.state.ResultState
import com.eoa.tech.core.util.thread.TestSchedulerProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*

/**
 * Created by Anggit Prayogo on 13,September,2020
 * GitHub : https://github.com/anggit97
 */
class FavouriteDetailViewModelTest{

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Mock
    lateinit var movieDetail: Observer<List<MovieEntity>>

    @Mock
    lateinit var error: Observer<String>

    @Captor
    lateinit var argResultCaptor: ArgumentCaptor<List<MovieEntity>>

    @Captor
    lateinit var argErrorCaptor: ArgumentCaptor<String>


    @Mock
    lateinit var useCase: MovieUseCase
    private val schedulerProvider = TestSchedulerProvider()
    private lateinit var SUT: FavouriteDetailViewModel

    private val movieId = "1"

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(schedulerProvider.ui())

        SUT = FavouriteDetailViewModel(useCase, schedulerProvider)

        observeMovies()
    }

    private fun observeMovies() {
        SUT.resultDetailFromDb.observeForever(movieDetail)
        SUT.error.observeForever(error)
    }

    @Test
    fun `get favourite movie by id should return response of correct movie favourite`() = runBlocking {
        //given
        val response = ResultState.Success(TestDataSource.moviesEntityList)

        //when
        Mockito.`when`(useCase.getMovieDetailByMovieId(movieId.toInt()))
            .thenReturn(response)
        SUT.getMovieDetailDb(movieId)

        //assert
        Mockito.verify(movieDetail, Mockito.atLeastOnce()).onChanged(argResultCaptor.capture())
        assertEquals(response.data, argResultCaptor.allValues.first())
        Mockito.clearInvocations(useCase, movieDetail)
    }

    @Test
    fun `get favourite movie by id should return error`() = runBlocking {
        //given
        val response = ResultState.Error("Error", getResponseErrorValue())

        //when
        Mockito.`when`(useCase.getMovieDetailByMovieId(movieId.toInt())).thenReturn(response)
        SUT.getMovieDetailDb(movieId)

        //assert
        Mockito.verify(error, Mockito.atLeastOnce()).onChanged(argErrorCaptor.capture())
        assertEquals(response.error, argErrorCaptor.allValues.first())
        Mockito.clearInvocations(useCase, error)
    }
}