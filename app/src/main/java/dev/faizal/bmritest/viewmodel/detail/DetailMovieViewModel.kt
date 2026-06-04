package dev.faizal.bmritest.viewmodel.detail

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
class DetailMovieViewModel @Inject constructor(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    private val _uiState = MutableStateFlow(DetailMovieState())
    val uiState: StateFlow<DetailMovieState> = _uiState.asStateFlow()

    init {
        loadDetail()
        loadTrailers()
        loadNextReviewPage()
    }

    private fun loadDetail() {
        repository.getMovieDetail(movieId)
            .onEach { resource ->
                _uiState.update { s ->
                    when (resource) {
                        is Resource.Loading -> s.copy(isLoading = true, error = null)
                        is Resource.Success -> s.copy(isLoading = false, detail = resource.data)
                        is Resource.Error -> s.copy(
                            isLoading = false,
                            error = resource.throwable.toUserMessage()
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }
    private fun loadTrailers() {
        repository.getMovieTrailers(movieId)
            .onEach { resource ->
                if (resource is Resource.Success) {
                    _uiState.update { it.copy(trailers = resource.data) }
                }
            }
            .launchIn(viewModelScope)
    }

    fun loadNextReviewPage() {
        val s = _uiState.value
        if (s.isLoadingMoreReviews || !s.canPaginateReviews) return

        repository.getMovieReviews(movieId, s.reviewPage)
            .onEach { resource ->
                _uiState.update { state ->
                    when (resource) {
                        is Resource.Loading ->
                            state.copy(isLoadingMoreReviews = true)
                        is Resource.Success -> {
                            val page = resource.data
                            state.copy(
                                isLoadingMoreReviews = false,
                                reviews = (state.reviews + page.reviews).distinctBy { it.id },
                                reviewPage = page.page + 1,
                                canPaginateReviews = page.page < page.totalPages
                            )
                        }
                        is Resource.Error ->
                            state.copy(isLoadingMoreReviews = false)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun retry() {
        _uiState.update { it.copy(error = null) }
        loadDetail()
        loadTrailers()
        loadNextReviewPage()
    }
}