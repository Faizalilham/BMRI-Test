package dev.faizal.core.data.remote.dto.genre

import com.google.gson.annotations.SerializedName

data class GenreResponseDto(
    @SerializedName("genres") val genres: List<GenreDto>
)