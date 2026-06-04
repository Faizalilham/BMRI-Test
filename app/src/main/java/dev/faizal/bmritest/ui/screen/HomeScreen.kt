package dev.faizal.bmritest.ui.screen


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.faizal.bmritest.ui.component.ErrorRetry
import dev.faizal.bmritest.ui.component.GenreChip
import dev.faizal.bmritest.ui.component.MovieCard
import dev.faizal.bmritest.ui.component.isNearBottom
import dev.faizal.bmritest.viewmodel.home.HomeMovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: HomeMovieViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()

    val nearBottom by gridState.isNearBottom()
    LaunchedEffect(nearBottom) {
        if (nearBottom) viewModel.loadNextPage()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Movies")
                }
            )
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            if (uiState.isLoadingGenres) {
                LinearProgressIndicator(Modifier.fillMaxWidth())
            } else {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.genres, key = { it.id }) { genre ->
                        GenreChip(
                            text = genre.name,
                            selected = genre.id == uiState.selectedGenreId,
                            onClick = { viewModel.selectGenre(genre.id) }
                        )
                    }
                }
            }

            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when {
                    uiState.isLoadingMovies && uiState.movies.isEmpty() ->
                        CircularProgressIndicator()

                    uiState.movieError != null && uiState.movies.isEmpty() ->
                        ErrorRetry(message = uiState.movieError!!, onRetry = viewModel::loadNextPage)

                    else -> LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = gridState,
                        contentPadding = PaddingValues(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.movies, key = { it.id }) { movie ->
                            MovieCard(movie = movie, onClick = { onMovieClick(movie.id) })
                        }
                        if (uiState.isLoadingMore) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Box(Modifier.fillMaxWidth().padding(16.dp), Alignment.Center) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}