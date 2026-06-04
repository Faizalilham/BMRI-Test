package dev.faizal.core.data.remote.dto.movie

import com.google.gson.annotations.SerializedName

data class DiscoverResponseDto(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieDto>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)