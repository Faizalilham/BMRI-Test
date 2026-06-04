package dev.faizal.bmritest.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable data object Home : Screen()

    @Serializable data class Detail(val movieId : Int) : Screen()
}

