package dev.faizal.bmritest.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable data object Genre : Screen()

    @Serializable data class Movie(val genreId : Int, val genreName : String) : Screen()

    @Serializable data class Detail(val movieId : Int) : Screen()
}

