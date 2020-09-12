package com.anggitprayogo.movon.data.routes

import com.anggitprayogo.movon.data.remote.MoviesResponse
import retrofit2.Response
import retrofit2.http.Headers


/**
 * Created by Anggit Prayogo on 12,September,2020
 * GitHub : https://github.com/anggit97
 */
interface MovieDBService {

    @Headers("movie/popular")
    suspend fun getPopularMovies() : Response<MoviesResponse>

    @Headers("movie/top_rated")
    suspend fun getTopRatedMovies() : Response<MoviesResponse>

    @Headers("movie/now_playing")
    suspend fun getNowPlayingMovies() : Response<MoviesResponse>
}