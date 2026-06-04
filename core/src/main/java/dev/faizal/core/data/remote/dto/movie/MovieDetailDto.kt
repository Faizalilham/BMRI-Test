package dev.faizal.core.data.remote.dto.movie

import com.google.gson.annotations.SerializedName
import dev.faizal.core.data.remote.dto.genre.GenreDto

data class MovieDetailDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?,
    @SerializedName("tagline") val tagline: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("homepage") val homepage: String?,
    @SerializedName("genres") val genres: List<GenreDto>?
)