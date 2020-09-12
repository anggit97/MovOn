package com.anggitprayogo.movon.data.routes

import com.anggitprayogo.movon.data.remote.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET


/**
 * Created by Anggit Prayogo on 12,September,2020
 * GitHub : https://github.com/anggit97
 */
interface MovieDBService {

    @GET("movie/popular")
    suspend fun getPopularMovies() : Response<MoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies() : Response<MoviesResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies() : Response<MoviesResponse>
}