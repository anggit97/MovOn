package com.anggitprayogo.movon.domain

import com.anggitprayogo.movon.TestDataSource
import com.anggitprayogo.movon.data.repository.MovieRepository
import com.eoa.tech.core.util.state.ResultState
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.Response
import java.net.ConnectException

/**
 * Created by Anggit Prayogo on 13,September,2020
 * GitHub : https://github.com/anggit97
 */
class MovieUseCaseTest {

    private var movieRepository = mock(MovieRepository::class.java)
    private val movieId = "1"
    private lateinit var SUT: MovieUseCase

    @Before
    fun setUp() {
        SUT = MovieUseCase(movieRepository)
    }

    @Test
    fun `get popular movie should return success`() {
        val actual = ResultState.Success(TestDataSource.movieResponse)

        val result = runBlocking {

            `when`(movieRepository.getPopularMovies())
                .thenReturn(Response.success(TestDataSource.movieResponse))

            SUT.getPopularMovie()
        }

        assert(actual == result)
    }

    @Test
    fun `get now playing movie should return success`() {
        val actual = ResultState.Success(TestDataSource.movieResponse)

        val result = runBlocking {

            `when`(movieRepository.getNowPlayingMovies())
                .thenReturn(Response.success(TestDataSource.movieResponse))

            SUT.getNowPlayingMovies()
        }

        assert(actual == result)
    }

    @Test
    fun `get top rated should return success`() {
        val actual = ResultState.Success(TestDataSource.movieResponse)

        val result = runBlocking {

            `when`(movieRepository.getTopRatedMovies())
                .thenReturn(Response.success(TestDataSource.movieResponse))

            SUT.getTopRatedMovies()
        }

        assert(actual == result)
    }

    @Test
    fun `get detail movie should return success`() {
        val actual = ResultState.Success(TestDataSource.movieDetail)
        val result = runBlocking {
            `when`(
                movieRepository.getDetailMovie(movieId)
            ).thenReturn(Response.success(TestDataSource.movieDetail))
            SUT.getDetailMovie(movieId)
        }
        assert(actual == result)
    }

    @Test
    fun `get favourite movie should return success`() {
        val actual = ResultState.Success(TestDataSource.moviesEntityList)
        val result = runBlocking {
            `when`(
                movieRepository.fetchAllMoviesDao()
            ).thenReturn(TestDataSource.moviesEntityList)
            SUT.getFavouriteMovie()
        }
        assert(actual == result)
    }

    @Test
    fun `get popular movie should return error "network exception error"`() {
        val actual = TestDataSource.networkError
        val result = runBlocking {
            BDDMockito.given(movieRepository.getPopularMovies()).willAnswer {
                throw ConnectException()
            }
            SUT.getPopularMovie()
        }
        assert(actual == result)
    }
}