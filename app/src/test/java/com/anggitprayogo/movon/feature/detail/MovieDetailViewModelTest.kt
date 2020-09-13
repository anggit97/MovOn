package com.anggitprayogo.movon.feature.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.anggitprayogo.movon.TestDataSource
import com.anggitprayogo.movon.data.local.entity.MovieEntity
import com.anggitprayogo.movon.data.remote.MovieDetail
import com.anggitprayogo.movon.data.remote.MovieReviews
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
class MovieDetailViewModelTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Mock
    lateinit var movie: Observer<MovieDetail>

    @Mock
    lateinit var reviews: Observer<MovieReviews>

    @Mock
    lateinit var movieListDb: Observer<List<MovieEntity>>

    @Mock
    lateinit var error: Observer<String>

    @Mock
    lateinit var network: Observer<String>

    @Captor
    lateinit var argResultCaptor: ArgumentCaptor<MovieDetail>

    @Captor
    lateinit var argResultReviewsCaptor: ArgumentCaptor<MovieReviews>

    @Captor
    lateinit var argResultMovieListDbCaptor: ArgumentCaptor<List<MovieEntity>>

    @Captor
    lateinit var argNetworkCaptor: ArgumentCaptor<String>

    @Captor
    lateinit var argErrorCaptor: ArgumentCaptor<String>


    @Mock
    lateinit var useCase: MovieUseCase
    private val schedulerProvider = TestSchedulerProvider()
    private lateinit var SUT: MovieDetailViewModel

    private val movieId = "1"

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(schedulerProvider.ui())

        SUT = MovieDetailViewModel(useCase, schedulerProvider)

        observeMovie()
    }

    private fun observeMovie() {
        SUT.resultDetailMovie.observeForever(movie)
        SUT.resultReviews.observeForever(reviews)
        SUT.resultDetailFromDb.observeForever(movieListDb)
        SUT.error.observeForever(error)
        SUT.networkError.observeForever(network)
    }

    @Test
    fun `get movie by id should return response of correct movie`() = runBlocking {
        //given
        val response = ResultState.Success(TestDataSource.movieDetail)

        //when
        Mockito.`when`(useCase.getDetailMovie(movieId))
            .thenReturn(response)
        SUT.getMovieDetail(movieId)

        //assert
        Mockito.verify(movie, Mockito.atLeastOnce()).onChanged(argResultCaptor.capture())
        assertEquals(response.data, argResultCaptor.allValues.first())
        Mockito.clearInvocations(useCase, movie)
    }

    @Test
    fun `get reviews by id should return response of correct reviews`() = runBlocking {
        //given
        val response = ResultState.Success(TestDataSource.movieReviews)

        //when
        Mockito.`when`(useCase.getMovieReviewsByMovieId(movieId))
            .thenReturn(response)
        SUT.getReviewsByMovieId(movieId)

        //assert
        Mockito.verify(reviews, Mockito.atLeastOnce()).onChanged(argResultReviewsCaptor.capture())
        assertEquals(response.data, argResultReviewsCaptor.allValues.first())
        Mockito.clearInvocations(useCase, reviews)
    }

    @Test
    fun `get movie from db by id should return response of correct movie`() = runBlocking {
        //given
        val response = ResultState.Success(TestDataSource.moviesEntityList)

        //when
        Mockito.`when`(useCase.getMovieDetailByMovieId(movieId.toInt()))
            .thenReturn(response)
        SUT.getMovieDetailDb(movieId)

        //assert
        Mockito.verify(movieListDb, Mockito.atLeastOnce()).onChanged(argResultMovieListDbCaptor.capture())
        assertEquals(response.data, argResultMovieListDbCaptor.allValues.first())
        Mockito.clearInvocations(useCase, movieListDb)
    }

    @Test
    fun `get movie by id should return error`() = runBlocking {
        //given
        val response = ResultState.Error("Error", getResponseErrorValue())

        //when
        Mockito.`when`(useCase.getDetailMovie(movieId)).thenReturn(response)
        SUT.getMovieDetail(movieId)

        //assert
        Mockito.verify(error, Mockito.atLeastOnce()).onChanged(argErrorCaptor.capture())
        assertEquals(response.error, argErrorCaptor.allValues.first())
        Mockito.clearInvocations(useCase, error)
    }

    @Test
    fun `get movie by id should return error network`() = runBlocking {
        //given
        val returnValue = TestDataSource.networkError
        val expected = "Internet Bermasalah, silahkan periksa kembali koneksi anda"

        //when
        Mockito.`when`(useCase.getDetailMovie(movieId)).thenReturn(returnValue)
        SUT.getMovieDetail(movieId)

        //assert
        Mockito.verify(network, Mockito.atLeastOnce()).onChanged(argNetworkCaptor.capture())
        assertEquals(expected, argNetworkCaptor.allValues.first())
        Mockito.clearInvocations(useCase, network)
    }
}