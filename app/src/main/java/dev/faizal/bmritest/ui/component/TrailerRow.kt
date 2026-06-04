package dev.faizal.bmritest.ui.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.faizal.core.domain.model.video.Video

@Composable
fun TrailerRow(trailers: List<Video>) {
    val context = LocalContext.current
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(trailers, key = { it.id }) { trailer ->
            Column(
                Modifier
                    .width(240.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(trailer.youtubeUrl))
                        context.startActivity(intent)
                    }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    AsyncImage(
                        model = "https://img.youtube.com/vi/${trailer.youtubeKey}/hqdefault.jpg",
                        contentDescription = trailer.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Icon(
                        Icons.Default.PlayCircle,
                        contentDescription = "Putar",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }
                Text(trailer.name, style = MaterialTheme.typography.labelMedium,
                    maxLines = 2, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}