package dev.faizal.bmritest.viewmodel.detail

import dev.faizal.core.domain.model.movie.MovieDetail
import dev.faizal.core.domain.model.review.Review
import dev.faizal.core.domain.model.video.Video


data class DetailMovieState(
    val isLoading: Boolean = false,
    val detail: MovieDetail? = null,
    val trailers: List<Video> = emptyList(),
    val reviews: List<Review> = emptyList(),
    val isLoadingMoreReviews: Boolean = false,
    val reviewPage: Int = 1,
    val canPaginateReviews: Boolean = true,
    val error: String? = null
)