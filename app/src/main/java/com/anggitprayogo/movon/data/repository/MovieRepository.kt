package com.anggitprayogo.movon.data.repository

import com.anggitprayogo.movon.data.local.entity.MovieEntity
import com.anggitprayogo.movon.data.remote.MovieDetail
import com.anggitprayogo.movon.data.remote.MovieReviews
import com.anggitprayogo.movon.data.remote.MoviesResponse
import retrofit2.Response


/**
 * Created by Anggit Prayogo on 12,September,2020
 * GitHub : https://github.com/anggit97
 */
interface MovieRepository {

    /**
     * Remote
     */
    suspend fun getPopularMovies(): Response<MoviesResponse>

    suspend fun getTopRatedMovies(): Response<MoviesResponse>

    suspend fun getNowPlayingMovies(): Response<MoviesResponse>

    suspend fun getDetailMovie(movieId: String): Response<MovieDetail>

    suspend fun getMovieReviewsByMovieId(movieId: String): Response<MovieReviews>


    /**
     * Local
     */
    suspend fun fetchAllMoviesDao(): List<MovieEntity>

    suspend fun fetchMovieByMovieId(movieId: Int): List<MovieEntity>

    suspend fun insertMovie(movieEntity: MovieEntity)

    suspend fun deleteMovie(movieEntity: MovieEntity)
}