package com.tripod.durust.presentation.home.individuals

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tripod.durust.R
import kotlin.math.absoluteValue

@Composable
fun MusicRecommendations() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Music recommendations",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFEF5350) // Custom color for the title
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .width(385.dp)
                .height(250.dp) // Adjust height as needed
                .padding(16.dp) // Inner padding inside the box
        ) {
            MusicCarouselBase(
                inactiveMusic = MusicCategory.JAZZ,
                modifier = Modifier,
                isActive = true
            ) {

            }
        }
    }
}


enum class MusicCategory(val categoryName: String, val imageResId: Int, val youtubeLink: String) {
    JAZZ("Jazz", R.drawable.musicjazz, "https://www.youtube.com/results?search_query=Jazz+Music"),
    MEDITATION_AND_MINDFULNESS(
        "Meditation and Mindfulness",
        R.drawable.musicmeditation,
        "https://www.youtube.com/results?search_query=Meditation+Music"
    ),
    NATURE(
        "Nature",
        R.drawable.musicnature,
        "https://www.youtube.com/results?search_query=Nature+Sounds"
    ),
    CLASSICAL(
        "Classical",
        R.drawable.musicindianclassical,
        "https://www.youtube.com/results?search_query=Indian+Classical+Music"
    ),
    INDIE(
        "Indie",
        R.drawable.musicindie,
        "https://www.youtube.com/results?search_query=Indie+Music"
    ),
    OLD_BOLLYWOOD(
        "Old Bollywood",
        R.drawable.musicoldbollywood,
        "https://www.youtube.com/results?search_query=Old+Bollywood+Songs"
    );
}

// Usage example:
fun main() {
    for (category in MusicCategory.entries) {
        println("Category: ${category.categoryName}, ImageResId: ${category.imageResId}, YouTube Link: ${category.youtubeLink}")
    }
}

@Composable
fun MusicCard(music: MusicCategory, isSelected: Boolean, modifier: Modifier) {
    val context = LocalContext.current
    Column {
        Box(modifier = Modifier
            .clickable {
                val uri = Uri.parse(music.youtubeLink)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            }
            .width(if (isSelected) 144.57143.dp else 144.dp)
            .height(if (isSelected) (184.dp) else 160.dp)
            .animateContentSize()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)) {
            AsyncImage(
                model = music.imageResId,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun MusicCarouselBase(
    inactiveMusic: MusicCategory,
    modifier: Modifier,
    isActive: Boolean,
    onMusicSelected: (MusicCategory) -> Unit
) {
    val musicCategories = if (isActive) MusicCategory.values().toList() else listOf(inactiveMusic)
    val pagerState = rememberPagerState() { musicCategories.size }
    var selectedMusic = musicCategories[pagerState.currentPage]

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 64.dp),
        modifier = modifier.border(1.dp, Color(0xFFAED5FF), RoundedCornerShape(16.dp))
    ) { page ->
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .graphicsLayer {
                    val pageOffset = pagerState.currentPageOffsetFraction.absoluteValue
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .padding(8.dp)
                .fillMaxSize()
        ) {
            MusicCard(
                music = musicCategories[page],
                isSelected = (selectedMusic == musicCategories[page]),
                modifier = Modifier
            )
        }
    }

    // Automatically select the center music
    LaunchedEffect(pagerState.currentPage) {
        selectedMusic = musicCategories[pagerState.currentPage]
        onMusicSelected(selectedMusic)
    }
}
