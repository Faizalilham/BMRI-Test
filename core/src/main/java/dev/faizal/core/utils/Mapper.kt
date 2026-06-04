package dev.faizal.core.utils

import dev.faizal.core.data.remote.dto.genre.*
import dev.faizal.core.data.remote.dto.movie.*
import dev.faizal.core.data.remote.dto.review.*
import dev.faizal.core.data.remote.dto.video.VideoDto
import dev.faizal.core.domain.model.genre.*
import dev.faizal.core.domain.model.movie.*
import dev.faizal.core.domain.model.review.*
import dev.faizal.core.domain.model.video.Video


fun GenreDto.toDomain() = Genre(id = id, name = name)

fun MovieDto.toDomain() = Movie(
    id = id,
    title = title.orEmpty(),
    overview = overview.orEmpty(),
    posterUrl = posterPath?.let { Constants.IMAGE_BASE_URL + it }.orEmpty(),
    backdropUrl = backdropPath?.let { Constants.BACKDROP_BASE_URL + it }.orEmpty(),
    releaseDate = releaseDate.orEmpty(),
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0
)

fun DiscoverResponseDto.toDomain() = MoviePage(
    page = page,
    totalPages = totalPages,
    movies = results.map { it.toDomain() }
)

fun MovieDetailDto.toDomain() = MovieDetail(
    id = id,
    title = title.orEmpty(),
    overview = overview.orEmpty(),
    posterUrl = posterPath?.let { Constants.IMAGE_BASE_URL + it }.orEmpty(),
    backdropUrl = backdropPath?.let { Constants.BACKDROP_BASE_URL + it }.orEmpty(),
    releaseDate = releaseDate.orEmpty(),
    runtime = runtime ?: 0,
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0,
    tagline = tagline.orEmpty(),
    status = status.orEmpty(),
    genres = genres?.map { it.toDomain() } ?: emptyList()
)

fun VideoDto.toDomain() = Video(
    id = id,
    name = name,
    youtubeKey = key,
    youtubeUrl = Constants.YOUTUBE_BASE_URL + key,
    type = type
)

fun ReviewDto.toDomain() = Review(
    id = id,
    author = author,
    content = content,
    rating = authorDetails?.rating,
    avatarUrl = authorDetails?.avatarPath?.let { path ->
        if (path.startsWith("/http")) path.removePrefix("/")
        else Constants.IMAGE_BASE_URL + path
    },
    createdAt = createdAt.orEmpty()
)

fun ReviewsResponseDto.toDomain() = ReviewPage(
    page = page,
    totalPages = totalPages,
    reviews = results.map { it.toDomain() }
)