package com.anggitprayogo.movon.data.remote

import com.anggitprayogo.movon.BuildConfig
import com.google.gson.annotations.SerializedName


/**
 * Created by Anggit Prayogo on 13,September,2020
 * GitHub : https://github.com/anggit97
 */
data class MovieDetail(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: Any?,
    @SerializedName("budget")
    val budget: Int?,
    @SerializedName("genres")
    val genres: List<Genre>?,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>?,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("revenue")
    val revenue: Int?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
) {
    fun getMetaData(): String {
        if (genres.isNullOrEmpty()) {
            return "Genre is not defined"
        }
        val joinedGenre = genres.map { it.name }.toList()
        return joinedGenre.joinToString(",")
    }

    fun getBannerMovie(): String {
        return BuildConfig.POSTER_IMAGE_URL + backdropPath
    }

    fun getPosterMovie(): String{
        return BuildConfig.POSTER_IMAGE_URL + posterPath
    }

    fun getImdbRating(): Double? {
        return (voteAverage?.div(10.0))?.times(5.0)
    }
}

data class Genre(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)

data class ProductionCompany(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: String?
)

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String?,
    @SerializedName("name")
    val name: String?
)

data class SpokenLanguage(
    @SerializedName("iso_639_1")
    val iso6391: String?,
    @SerializedName("name")
    val name: String?
)