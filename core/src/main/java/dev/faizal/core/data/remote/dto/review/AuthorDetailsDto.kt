package dev.faizal.core.data.remote.dto.review

import com.google.gson.annotations.SerializedName

data class AuthorDetailsDto(
    @SerializedName("rating") val rating: Double?,
    @SerializedName("avatar_path") val avatarPath: String?
)