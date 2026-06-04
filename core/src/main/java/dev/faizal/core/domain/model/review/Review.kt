package dev.faizal.core.domain.model.review

data class Review(
    val id: String,
    val author: String,
    val content: String,
    val rating: Double?,
    val avatarUrl: String?,
    val createdAt: String
)