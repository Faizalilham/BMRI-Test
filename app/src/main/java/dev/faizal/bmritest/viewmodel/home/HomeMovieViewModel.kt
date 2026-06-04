package dev.faizal.bmritest.viewmodel.home

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
class HomeMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(HomeMovieState())
    val uiState : StateFlow<HomeMovieState> = _uiState.asStateFlow()

    init {
            android.util.Log.d("HOMEVM", "init terpanggil, mulai loadGenres")
        loadGenres()
    }


    private fun loadGenres(){
        movieRepository.getGenres().onEach { resource ->
            when(resource){
                is Resource.Loading -> _uiState.update { it.copy(isLoadingGenres = true, genreError = null) }
                is Resource.Success -> {
                    android.util.Log.d("HOMEVM", "genre sukses: ${resource.data.size} item")
                    _uiState.update { it.copy(isLoadingGenres = false, genreError = null,genres = resource.data) }
                    resource.data.firstOrNull()?.let { selectGenre(it.id) }
                }
                is Resource.Error -> _uiState.update {
                    android.util.Log.e("HOMEVM", "genre error: ${resource.throwable}")
                    it.copy(isLoadingGenres = false, genreError = resource.throwable.toUserMessage())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun selectGenre(genreId: Int) {
        if (_uiState.value.selectedGenreId == genreId) return
        _uiState.update {
            it.copy(
                selectedGenreId = genreId,
                movies = emptyList(),
                currentPage = 1,
                canPaginate = true,
                movieError = null
            )
        }
        loadMovies(genreId, page = 1)
    }

    fun loadNextPage() {
        val s = _uiState.value
        val genreId = s.selectedGenreId ?: return
        if (s.isLoadingMovies || s.isLoadingMore || !s.canPaginate) return
        loadMovies(genreId, s.currentPage)
    }

    private fun loadMovies(genreId: Int, page: Int) {
        movieRepository.discoverMovies(genreId = genreId, page = page)
            .onEach { resource ->
                _uiState.update { s ->
                    when (resource) {
                        is Resource.Loading ->
                            if (page == 1) s.copy(isLoadingMovies = true, movieError = null)
                            else s.copy(isLoadingMore = true, movieError = null)
                        is Resource.Success -> {
                            val p = resource.data
                            val combined = (s.movies + p.movies).distinctBy { it.id }
                            s.copy(
                                isLoadingMovies = false,
                                isLoadingMore = false,
                                movies = combined,
                                currentPage = p.page + 1,
                                canPaginate = p.page < p.totalPages
                            )
                        }
                        is Resource.Error -> s.copy(
                            isLoadingMovies = false,
                            isLoadingMore = false,
                            movieError = resource.throwable.toUserMessage()
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

}