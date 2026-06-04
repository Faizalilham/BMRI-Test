package dev.faizal.core.domain.model.movie

import dev.faizal.core.domain.model.genre.Genre

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val backdropUrl: String,
    val releaseDate: String,
    val runtime: Int,
    val voteAverage: Double,
    val voteCount: Int,
    val tagline: String,
    val status: String,
    val genres: List<Genre>
)