package dev.faizal.core.data.remote.service

import dev.faizal.core.data.remote.dto.genre.GenreResponseDto
import dev.faizal.core.data.remote.dto.movie.DiscoverResponseDto
import dev.faizal.core.data.remote.dto.movie.MovieDetailDto
import dev.faizal.core.data.remote.dto.review.ReviewsResponseDto
import dev.faizal.core.data.remote.dto.video.VideosResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("language") language: String = "en"
    ): GenreResponseDto

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US"
    ): DiscoverResponseDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ): MovieDetailDto

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ): VideosResponseDto

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): ReviewsResponseDto
}