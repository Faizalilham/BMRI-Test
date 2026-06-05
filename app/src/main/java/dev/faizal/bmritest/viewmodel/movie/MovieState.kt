package dev.faizal.bmritest.viewmodel.movie


import dev.faizal.core.domain.model.movie.Movie

data class MovieState(
    val isLoading : Boolean = false,
    val isLoadingMore : Boolean = false,
    val movies : List<Movie> = emptyList(),
    val currentPage : Int = 1,
    val canPaginate : Boolean = true,
    val error : String? = null
)