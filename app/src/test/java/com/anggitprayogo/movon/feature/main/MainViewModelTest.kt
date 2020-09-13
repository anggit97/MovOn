package com.anggitprayogo.movon.feature.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.anggitprayogo.movon.TestDataSource
import com.anggitprayogo.movon.data.remote.MoviesResponse
import com.anggitprayogo.movon.domain.MovieUseCase
import com.anggitprayogo.movon.getResponseErrorValue
import com.eoa.tech.core.util.state.ResultState
import com.eoa.tech.core.util.thread.TestSchedulerProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.mock

/**
 * Created by Anggit Prayogo on 13,September,2020
 * GitHub : https://github.com/anggit97
 */
class MainViewModelTest {

    private val scheduler = TestSchedulerProvider()
    private val useCase = mock(MovieUseCase::class.java)
    private lateinit var SUT: MainViewModel

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Mock
    lateinit var popularMovies: Observer<MoviesResponse>

    @Mock
    lateinit var topRatedMovies: Observer<MoviesResponse>

    @Mock
    lateinit var nowPlayingMovies: Observer<MoviesResponse>

    @Mock
    lateinit var error: Observer<String>

    @Mock
    lateinit var network: Observer<String>

    @Captor
    lateinit var argResultCaptor: ArgumentCaptor<MoviesResponse>

    @Captor
    lateinit var argNetworkCaptor: ArgumentCaptor<String>

    @Captor
    lateinit var argErrorCaptor: ArgumentCaptor<String>

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(scheduler.ui())
        SUT = MainViewModel(useCase, scheduler)
        observeViewModel()
    }

    private fun observeViewModel() {
        SUT.popularMovies.observeForever(popularMovies)
        SUT.topRatedMovies.observeForever(topRatedMovies)
        SUT.nowPlayingMovies.observeForever(nowPlayingMovies)
        SUT.error.observeForever(error)
        SUT.networkError.observeForever(network)
    }

    @Test
    fun `get popular movies should return response of correct movies popular`() = runBlocking {
        //given
        val response = ResultState.Success(TestDataSource.movieResponse)

        //when
        Mockito.`when`(useCase.getPopularMovie())
            .thenReturn(response)
        SUT.getPopularMovies()

        //assert
        Mockito.verify(popularMovies, Mockito.atLeastOnce()).onChanged(argResultCaptor.capture())
        Assert.assertEquals(response.data.movies, argResultCaptor.allValues.first().movies)
        Mockito.clearInvocations(useCase, popularMovies)
    }

    @Test
    fun `get now playing movies should return response of correct now playing movies`() = runBlocking {
        //given
        val response = ResultState.Success(TestDataSource.movieResponse)

        //when
        Mockito.`when`(useCase.getNowPlayingMovies())
            .thenReturn(response)
        SUT.getNowPlayingMovies()

        //assert
        Mockito.verify(nowPlayingMovies, Mockito.atLeastOnce()).onChanged(argResultCaptor.capture())
        Assert.assertEquals(response.data.movies, argResultCaptor.allValues.first().movies)
        Mockito.clearInvocations(useCase, nowPlayingMovies)
    }

    @Test
    fun `get top rated movies should return response of correct top rated movies`() = runBlocking {
        //given
        val response = ResultState.Success(TestDataSource.movieResponse)

        //when
        Mockito.`when`(useCase.getTopRatedMovies())
            .thenReturn(response)
        SUT.getTopRatedMovies()

        //assert
        Mockito.verify(topRatedMovies, Mockito.atLeastOnce()).onChanged(argResultCaptor.capture())
        Assert.assertEquals(response.data.movies, argResultCaptor.allValues.first().movies)
        Mockito.clearInvocations(useCase, topRatedMovies)
    }

    @Test
    fun `get movies should return error`() = runBlocking {
        //given
        val response = ResultState.Error("Error", getResponseErrorValue())

        //when
        Mockito.`when`(useCase.getPopularMovie()).thenReturn(response)
        SUT.getPopularMovies()

        //assert
        Mockito.verify(error, Mockito.atLeastOnce()).onChanged(argErrorCaptor.capture())
        Assert.assertEquals(response.error, argErrorCaptor.allValues.first())
        Mockito.clearInvocations(useCase, error)
    }

    @Test
    fun `get movies should return error network`() = runBlocking {
        //given
        val returnValue = TestDataSource.networkError
        val expected = ResultState.NetworkError("Internet Bermasalah, silahkan periksa kembali koneksi anda")

        //when
        Mockito.`when`(useCase.getPopularMovie()).thenReturn(returnValue)
        SUT.getPopularMovies()

        //assert
        Mockito.verify(network, Mockito.atLeastOnce()).onChanged(argNetworkCaptor.capture())
        Assert.assertEquals(expected.error, argNetworkCaptor.allValues.first())
        Mockito.clearInvocations(useCase, network)
    }
}