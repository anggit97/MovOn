package com.anggitprayogo.movon.domain

import com.anggitprayogo.movon.data.remote.MovieDetail
import com.anggitprayogo.movon.data.remote.MovieReviews
import com.anggitprayogo.movon.data.remote.MoviesResponse
import com.anggitprayogo.movon.data.repository.MovieRepository
import com.eoa.tech.core.util.ext.safeApiCall
import com.eoa.tech.core.util.ext.safeApiErrorHandling
import com.eoa.tech.core.util.state.ResultState
import javax.inject.Inject


/**
 * Created by Anggit Prayogo on 12,September,2020
 * GitHub : https://github.com/anggit97
 */
class MovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend fun getPopularMovie(): ResultState<MoviesResponse> {
        return safeApiCall {
            val result = movieRepository.getPopularMovies()
            if (result.isSuccessful) {
                ResultState.Success(result.body()!!)
            } else {
                safeApiErrorHandling(result)
            }
        }
    }

    suspend fun getTopRatedMovies(): ResultState<MoviesResponse> {
        return safeApiCall {
            val result = movieRepository.getTopRatedMovies()
            if (result.isSuccessful) {
                ResultState.Success(result.body()!!)
            } else {
                safeApiErrorHandling(result)
            }
        }
    }

    suspend fun getNowPlayingMovies(): ResultState<MoviesResponse> {
        return safeApiCall {
            val result = movieRepository.getNowPlayingMovies()
            if (result.isSuccessful) {
                ResultState.Success(result.body()!!)
            } else {
                safeApiErrorHandling(result)
            }
        }
    }

    suspend fun getDetailMovie(movieId: String): ResultState<MovieDetail> {
        return safeApiCall {
            val result = movieRepository.getDetailMovie(movieId)
            if (result.isSuccessful) {
                ResultState.Success(result.body()!!)
            } else {
                safeApiErrorHandling(result)
            }
        }
    }

    suspend fun getMovieReviewsByMovieId(movieId: String): ResultState<MovieReviews> {
        return safeApiCall {
            val result = movieRepository.getMovieReviewsByMovieId(movieId)
            try {
                ResultState.Success(result.body()!!)
            } catch (e: Exception) {
                safeApiErrorHandling(result)
            }
        }
    }
}