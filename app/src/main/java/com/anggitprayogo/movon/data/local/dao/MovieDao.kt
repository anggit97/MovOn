package com.anggitprayogo.movon.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.anggitprayogo.movon.data.local.entity.MovieEntity


/**
 * Created by Anggit Prayogo on 12,September,2020
 * GitHub : https://github.com/anggit97
 */
@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    suspend fun fetchAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE uid=:uid")
    suspend fun fetchMovieById(uid: Long): MovieEntity

    @Query("SELECT * FROM movies WHERE movie_id=:movieId")
    suspend fun fetchMovieByMovieId(movieId: Int): List<MovieEntity>

    @Insert
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Delete
    suspend fun deleteMovie(movieEntity: MovieEntity)
}