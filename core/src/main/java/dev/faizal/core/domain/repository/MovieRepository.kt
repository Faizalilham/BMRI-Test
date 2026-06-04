package dev.faizal.core.domain.repository

import dev.faizal.core.domain.model.genre.Genre
import dev.faizal.core.domain.model.movie.MovieDetail
import dev.faizal.core.domain.model.movie.MoviePage
import dev.faizal.core.domain.model.review.ReviewPage
import dev.faizal.core.domain.model.video.Video
import dev.faizal.core.utils.network.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getGenres(): Flow<Resource<List<Genre>>>
    fun discoverMovies(genreId: Int, page: Int): Flow<Resource<MoviePage>>
    fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>>
    fun getMovieTrailers(movieId: Int): Flow<Resource<List<Video>>>
    fun getMovieReviews(movieId: Int, page: Int): Flow<Resource<ReviewPage>>
}