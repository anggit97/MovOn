package com.anggitprayogo.movon.data.repository

import com.anggitprayogo.movon.data.local.dao.MovieDao
import com.anggitprayogo.movon.data.local.entity.MovieEntity
import com.anggitprayogo.movon.data.remote.MovieDetail
import com.anggitprayogo.movon.data.remote.MovieReviews
import com.anggitprayogo.movon.data.remote.MoviesResponse
import com.anggitprayogo.movon.data.routes.MovieDBService
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteService: MovieDBService,
    private val movieDao: MovieDao
) : MovieRepository {

    /**
     * Remote
     */
    override suspend fun getPopularMovies(): Response<MoviesResponse> {
        return movieRemoteService.getPopularMovies()
    }

    override suspend fun getTopRatedMovies(): Response<MoviesResponse> {
        return movieRemoteService.getTopRatedMovies()
    }

    override suspend fun getNowPlayingMovies(): Response<MoviesResponse> {
        return movieRemoteService.getNowPlayingMovies()
    }

    override suspend fun getDetailMovie(movieId: String): Response<MovieDetail> {
        return movieRemoteService.getDetailMovie(movieId)
    }

    override suspend fun getMovieReviewsByMovieId(movieId: String): Response<MovieReviews> {
        return movieRemoteService.getMovieReviewsByMovieId(movieId)
    }


    /**
     * Local
     */
    override suspend fun fetchAllMoviesDao(): List<MovieEntity> {
        return movieDao.fetchAllMovies()
    }

    override suspend fun fetchMovieByMovieId(movieId: Int): List<MovieEntity> {
        return movieDao.fetchMovieByMovieId(movieId)
    }

    override suspend fun insertMovie(movieEntity: MovieEntity) {
        return movieDao.insertMovie(movieEntity)
    }

    override suspend fun deleteMovie(movieEntity: MovieEntity) {
        return movieDao.deleteMovie(movieEntity)
    }
}