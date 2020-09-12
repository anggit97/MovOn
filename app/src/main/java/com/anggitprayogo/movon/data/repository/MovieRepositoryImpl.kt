package com.anggitprayogo.movon.data.repository

import com.anggitprayogo.movon.data.remote.MoviesResponse
import com.anggitprayogo.movon.data.routes.MovieDBService
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private var movieRemoteService: MovieDBService
) : MovieRepository {

    override suspend fun getPopularMovies(): Response<MoviesResponse> {
        return movieRemoteService.getPopularMovies()
    }

    override suspend fun getTopRatedMovies(): Response<MoviesResponse> {
        return movieRemoteService.getTopRatedMovies()
    }

    override suspend fun getNowPlayingMovies(): Response<MoviesResponse> {
        return movieRemoteService.getNowPlayingMovies()
    }
}