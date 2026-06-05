package dev.faizal.bmritest.viewmodel.genre

import dev.faizal.core.domain.model.genre.Genre

data class GenreState (
    val isLoading : Boolean = false,
    val genres : List<Genre> = emptyList(),
    val error : String? = null
)