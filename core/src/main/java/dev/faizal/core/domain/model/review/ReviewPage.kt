package dev.faizal.core.domain.model.review

data class ReviewPage(
    val page: Int,
    val totalPages: Int,
    val reviews: List<Review>
)