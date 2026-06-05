package dev.faizal.bmritest.ui.screen

import dev.faizal.core.domain.model.movie.MovieDetail
import dev.faizal.core.domain.model.review.Review
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import dev.faizal.bmritest.ui.component.ErrorRetry
import dev.faizal.bmritest.ui.component.PrimaryInfo
import dev.faizal.bmritest.ui.component.ReviewItem
import dev.faizal.bmritest.ui.component.SectionTitle
import dev.faizal.bmritest.ui.component.TrailerRow
import dev.faizal.bmritest.ui.component.isNearBottom
import dev.faizal.bmritest.viewmodel.detail.DetailMovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    onBack: () -> Unit,
    viewModel: DetailMovieViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    val nearBottom by listState.isNearBottom()
    LaunchedEffect(nearBottom) {
        if (nearBottom) viewModel.loadNextReviewPage()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.detail?.title ?: "Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        when {
            uiState.isLoading && uiState.detail == null ->
                Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) {
                    CircularProgressIndicator()
                }
            uiState.error != null && uiState.detail == null ->
                Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) {
                    ErrorRetry(message = uiState.error!!, onRetry = viewModel::retry)
                }
            else -> LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                uiState.detail?.let { detail ->
                    item { PrimaryInfo(detail) }
                }
                if (uiState.trailers.isNotEmpty()) {
                    item {
                        SectionTitle("Trailer")
                        TrailerRow(uiState.trailers)
                    }
                }
                item { SectionTitle("Ulasan Pengguna") }

                if (uiState.reviews.isEmpty() && !uiState.isLoadingMoreReviews) {
                    item { Text("Belum ada ulasan.", Modifier.padding(16.dp)) }
                }

                items(uiState.reviews, key = { it.id }) { review ->
                    ReviewItem(review)
                }

                if (uiState.isLoadingMoreReviews) {
                    item {
                        Box(Modifier.fillMaxWidth().padding(16.dp), Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}