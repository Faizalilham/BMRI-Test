package dev.faizal.core.domain.model.video

data class Video(
    val id: String,
    val name: String,
    val youtubeKey: String,   // dipakai kalau mau embed YouTube player
    val youtubeUrl: String,   // dipakai kalau mau buka di browser/app YouTube
    val type: String
)