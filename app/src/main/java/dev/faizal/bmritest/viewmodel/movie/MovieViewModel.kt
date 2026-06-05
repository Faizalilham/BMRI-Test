package dev.faizal.bmritest.viewmodel.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.faizal.core.domain.repository.MovieRepository
import dev.faizal.core.utils.exception.toUserMessage
import dev.faizal.core.utils.network.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _uiState = MutableStateFlow(MovieState())
    val uiState : StateFlow<MovieState> = _uiState.asStateFlow()

    private val genreId: Int = checkNotNull(savedStateHandle["genreId"])

    init {
        android.util.Log.d("HOMEVM", "init terpanggil, mulai loadGenres")
        loadNextPage()
    }

    fun loadNextPage() {
        val s = _uiState.value
        if (s.isLoading || s.isLoadingMore || !s.canPaginate) return
        loadMovies(genreId, s.currentPage)
    }

    private fun loadMovies(genreId: Int, page: Int) {
        movieRepository.discoverMovies(genreId = genreId, page = page)
            .onEach { resource ->
                _uiState.update { s ->
                    when (resource) {
                        is Resource.Loading ->
                            if (page == 1) s.copy(isLoading = true, error = null)
                            else s.copy(isLoadingMore = true, error = null)
                        is Resource.Success -> {
                            val p = resource.data
                            val combined = (s.movies + p.movies).distinctBy { it.id }
                            s.copy(
                                isLoading = false,
                                isLoadingMore = false,
                                movies = combined,
                                currentPage = p.page + 1,
                                canPaginate = p.page < p.totalPages
                            )
                        }
                        is Resource.Error -> s.copy(
                            isLoading = false,
                            isLoadingMore = false,
                            error = resource.throwable.toUserMessage()
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun retry() {
        _uiState.update { it.copy(error = null) }
        loadNextPage()
    }

}