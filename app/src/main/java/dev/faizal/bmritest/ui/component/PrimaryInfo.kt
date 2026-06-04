package dev.faizal.bmritest.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.faizal.core.domain.model.movie.MovieDetail
import kotlin.text.ifEmpty

@Composable
fun PrimaryInfo(detail: MovieDetail) {
    Column {
        AsyncImage(
            model = detail.backdropUrl.ifEmpty { detail.posterUrl },
            contentDescription = detail.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().aspectRatio(16f / 9f)
        )
        Column(Modifier.padding(16.dp)) {
            Text(detail.title, style = MaterialTheme.typography.headlineSmall)
            if (detail.tagline.isNotEmpty()) {
                Text(detail.tagline, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "⭐ ${"%.1f".format(detail.voteAverage)} (${detail.voteCount})  •  " +
                        "${detail.runtime} mnt  •  ${detail.releaseDate}",
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(Modifier.height(4.dp))
            Text(
                detail.genres.joinToString { it.name },
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(Modifier.height(12.dp))
            Text(detail.overview, style = MaterialTheme.typography.bodyMedium)
        }
    }
}