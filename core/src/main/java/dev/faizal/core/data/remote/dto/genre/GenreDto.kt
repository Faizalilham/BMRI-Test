package dev.faizal.core.data.remote.dto.genre

import com.google.gson.annotations.SerializedName

data class GenreDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
