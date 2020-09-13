package com.anggitprayogo.movon

import com.anggitprayogo.movon.data.local.entity.MovieEntity
import com.anggitprayogo.movon.data.remote.*
import com.eoa.tech.core.util.state.ResultState
import org.mockito.Mockito
import org.mockito.Mockito.mock


/**
 * Created by Anggit Prayogo on 13,September,2020
 * GitHub : https://github.com/anggit97
 */
object TestDataSource {

    val movieResponse = MoviesResponse(
        1,
        listOf(),
        1,
        1
    )

    val movieResponseList = listOf(
        mock(Movie::class.java)
    )

    val movieDetail = mock(MovieDetail::class.java)

    val movieReviews = MovieReviews(
        1, 1, listOf(
            mock(Review::class.java),
            mock(Review::class.java)
        ), 1, 2
    )

    val moviesEntityList = listOf(
        mock(MovieEntity::class.java),
        mock(MovieEntity::class.java)
    )

    val networkError = ResultState.NetworkError("Internet Bermasalah, silahkan periksa kembali koneksi anda")
}