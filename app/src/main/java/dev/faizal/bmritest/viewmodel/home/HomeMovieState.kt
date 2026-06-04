package dev.faizal.bmritest.viewmodel.home

import dev.faizal.core.domain.model.genre.Genre
import dev.faizal.core.domain.model.movie.Movie

data class HomeMovieState(
    val isLoadingGenres : Boolean = false,
    val genres : List<Genre> = emptyList(),
    val selectedGenreId : Int? = null,
    val isLoadingMovies : Boolean = false,
    val isLoadingMore : Boolean = false,
    val movies : List<Movie> = emptyList(),
    val currentPage : Int = 1,
    val canPaginate : Boolean = true,
    val genreError : String? = null,
    val movieError : String? = null
)