package dev.faizal.bmritest.viewmodel.genre

import androidx.compose.runtime.MutableState
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
class GenreViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(GenreState())

    val uiState : StateFlow<GenreState> = _uiState.asStateFlow()

    init {
        loadGenres()
    }

    fun loadGenres(){
        movieRepository.getGenres().onEach { resource ->
            _uiState.update {
                when(resource){
                    is Resource.Loading -> it.copy(isLoading = true,error = null)
                    is Resource.Success -> it.copy(isLoading = false,error = null, genres = resource.data)
                    is Resource.Error -> it.copy(
                        isLoading = false,
                        error = resource.throwable.toUserMessage()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}
