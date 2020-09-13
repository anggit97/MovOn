package com.anggitprayogo.movon.data.repository

import com.anggitprayogo.movon.data.remote.MovieDetail
import com.anggitprayogo.movon.data.remote.MovieReviews
import com.anggitprayogo.movon.data.remote.MoviesResponse
import retrofit2.Response


/**
 * Created by Anggit Prayogo on 12,September,2020
 * GitHub : https://github.com/anggit97
 */
interface MovieRepository {

    suspend fun getPopularMovies(): Response<MoviesResponse>

    suspend fun getTopRatedMovies(): Response<MoviesResponse>

    suspend fun getNowPlayingMovies(): Response<MoviesResponse>

    suspend fun getDetailMovie(movieId: String): Response<MovieDetail>

    suspend fun getMovieReviewsByMovieId(movieId: String): Response<MovieReviews>
}