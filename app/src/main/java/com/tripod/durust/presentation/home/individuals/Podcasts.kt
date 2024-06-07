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

enum class Podcast(val title: String, val imageResId: Int, val link: String) {
    PODCAST1(
        "Stronger By Science",
        R.drawable.strongerbyscience,
        "https://www.google.com/search?q=Stronger+By+Science+Podcast"
    ),
    PODCAST2(
        "The Show Up Fitness",
        R.drawable.showupfitness,
        "https://www.google.com/search?q=The+Show+Up+Fitness+Podcast"
    ),
    PODCAST3(
        "The Mental Illness Happy Hour",
        R.drawable.podcastmentalhappyhour,
        "https://www.google.com/search?q=The+Mental+Illness+Happy+Hour+Podcast"
    ),
    PODCAST4(
        "Ten Percent Happier",
        R.drawable.podcastthepercenthappier,
        "https://www.google.com/search?q=Ten+Percent+Happier+Podcast"
    ),
    PODCAST5(
        "The Consistency Project",
        R.drawable.podcasttheconsistencyproject,
        "https://www.google.com/search?q=The+Consistency+Project+Podcast"
    ),
    PODCAST6(
        "Hidden Brain",
        R.drawable.podcastthehiddenbrain,
        "https://www.google.com/search?q=Hidden+Brain+Podcast"
    );
}

// Usage example:
fun main() {
    for (podcast in Podcast.values()) {
        println("Title: ${podcast.title}, ImageResId: ${podcast.imageResId}, Link: ${podcast.link}")
    }
}

@Composable
fun PodcastCard(podcast: Podcast, isSelected: Boolean, modifier: Modifier) {
    val context = LocalContext.current
    Column {
        Box(modifier = Modifier
            .clickable {
                val uri = Uri.parse(podcast.link)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            }
            .width(if (isSelected) 144.57143.dp else 144.dp)
            .height(if (isSelected) (184.dp) else 160.dp)
            .animateContentSize()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)) {
            AsyncImage(
                model = podcast.imageResId,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun PodcastCarouselBase(
    inactivePodcast: Podcast,
    modifier: Modifier,
    isActive: Boolean,
    onPodcastSelected: (Podcast) -> Unit
) {
    val podcasts = if (isActive) Podcast.values().toList() else listOf(inactivePodcast)
    val pagerState = rememberPagerState() { podcasts.size }
    var selectedPodcast = podcasts[pagerState.currentPage]

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
            PodcastCard(
                podcast = podcasts[page],
                isSelected = (selectedPodcast == podcasts[page]),
                modifier = Modifier
            )
        }
    }

    // Automatically select the center podcast
    LaunchedEffect(pagerState.currentPage) {
        selectedPodcast = podcasts[pagerState.currentPage]
        onPodcastSelected(selectedPodcast)
    }
}

@Composable
fun PodcastRecommendations() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Podcast recommendations",
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
            PodcastCarouselBase(
                inactivePodcast = Podcast.PODCAST1,
                modifier = Modifier,
                isActive = true
            ) {

            }
        }
    }
}

@Composable
fun PodcastRecommendationsPreview() {
    PodcastRecommendations()
}
