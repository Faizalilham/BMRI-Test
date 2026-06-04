package dev.faizal.core.data.repository

import dev.faizal.core.data.remote.service.MovieApiService
import dev.faizal.core.domain.model.genre.Genre
import dev.faizal.core.domain.model.movie.MovieDetail
import dev.faizal.core.domain.model.movie.MoviePage
import dev.faizal.core.domain.model.review.ReviewPage
import dev.faizal.core.domain.model.video.Video
import dev.faizal.core.domain.repository.MovieRepository
import dev.faizal.core.utils.network.Resource
import dev.faizal.core.utils.network.apiFlow
import dev.faizal.core.utils.toDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService
): MovieRepository{

    override fun getGenres(): Flow<Resource<List<Genre>>> {
        return apiFlow { movieApiService.getGenres().genres.map { it.toDomain() } }
    }

    override fun discoverMovies(
        genreId: Int,
        page: Int
    ): Flow<Resource<MoviePage>> {
       return apiFlow { movieApiService.discoverMovies(genreId,page).toDomain() }
    }

    override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> {
        return apiFlow { movieApiService.getMovieDetail(movieId).toDomain() }
    }

    override fun getMovieTrailers(movieId: Int): Flow<Resource<List<Video>>> {
        return apiFlow { movieApiService.getMovieVideos(movieId).results.filter {
            it.site.equals("Youtube", ignoreCase = true)
        }.map { it.toDomain() } }
    }

    override fun getMovieReviews(
        movieId: Int,
        page: Int
    ): Flow<Resource<ReviewPage>> {
        return apiFlow { movieApiService.getMovieReviews(movieId,page).toDomain() }
    }

}