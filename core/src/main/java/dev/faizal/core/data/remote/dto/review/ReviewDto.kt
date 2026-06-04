package dev.faizal.core.data.remote.dto.review

import com.google.gson.annotations.SerializedName

data class ReviewDto(
    @SerializedName("id") val id: String,
    @SerializedName("author") val author: String,
    @SerializedName("content") val content: String,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("author_details") val authorDetails: AuthorDetailsDto?
)