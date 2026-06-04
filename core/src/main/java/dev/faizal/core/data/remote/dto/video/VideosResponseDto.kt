package dev.faizal.core.data.remote.dto.video

import com.google.gson.annotations.SerializedName

data class VideosResponseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val results: List<VideoDto>
)