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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tripod.durust.R
import kotlin.math.absoluteValue


@Composable
fun MovieRecommendations() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Film recommendations",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFEF5350) // Custom color for the title
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .width(385.dp)
                .height(250.dp) // Adjust height as needed
//                .border(
////                    width = 2.dp,
////                    color = Color(0xFFBBDEFB), // Border color
////                    shape = RoundedCornerShape(16.dp)
//                )
                .padding(16.dp) // Inner padding inside the box
        ) {
            MoviesCarouselBase(
                inactiveMovie = Movie.THREE_IDIOTS,
                modifier = Modifier,
                isActive = true
            ) {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MusicRecommendationsPreview() {
    MovieRecommendations()
}


enum class Movie(val movieName: String, val imageResId: Int, val link: String) {
    THE_PURSUIT_OF_HAPPYNESS(
        "The Pursuit of Happyness",
        R.drawable.moviepersuit,
        "https://www.google.com/search?q=The+Pursuit+of+Happyness"
    ),
    THREE_IDIOTS("3 Idiots", R.drawable.movie3idiot, "https://www.google.com/search?q=3+Idiots"),
    DEAD_POETS_SOCIETY(
        "Dead Poets Society",
        R.drawable.moviedeadpoets,
        "https://www.google.com/search?q=Dead+Poets+Society"
    ),
    CHHICHHORE(
        "Chhichhore",
        R.drawable.moviechhichore,
        "https://www.google.com/search?q=Chhichhore"
    ),
    PUSS_IN_BOOTS(
        "Puss in Boots",
        R.drawable.moviedussboss,
        "https://www.google.com/search?q=Puss+in+Boots"
    ),
    THE_SHAWSHANK_REDEMPTION(
        "The Shawshank Redemption",
        R.drawable.theshawshank,
        "https://www.google.com/search?q=The+Shawshank+Redemption"
    );
}


// Usage example:
fun main() {
    for (movie in Movie.values()) {
        println("Movie: ${movie.movieName}, ImageResId: ${movie.imageResId}, Link: ${movie.link}")
    }
}

@Composable
fun MoviesCarouselBase(
    inactiveMovie: Movie,
    modifier: Modifier,
    isActive: Boolean,
    onMovieSelected: (Movie) -> Unit
) {
    val movies = if (isActive) Movie.values().toList() else listOf(inactiveMovie)
    val pagerState = rememberPagerState() { movies.size }
    var selectedMovie = movies[pagerState.currentPage]

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
            MovieCard(
                movie = movies[page],
                isSelected = (selectedMovie == movies[page]),
                modifier = Modifier
            )
        }
    }

    // Automatically select the center movie
    LaunchedEffect(pagerState.currentPage) {
        selectedMovie = movies[pagerState.currentPage]
        onMovieSelected(selectedMovie)
    }
}

@Composable
fun MovieCard(movie: Movie, isSelected: Boolean, modifier: Modifier) {
    val context = LocalContext.current
    Column {
        Box(modifier = Modifier
            .clickable {
                val uri = Uri.parse(movie.link)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            }
            .width(if (isSelected) 144.57143.dp else 144.dp)
            .height(if (isSelected) (184.dp) else 160.dp)
            .animateContentSize()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)) {
            AsyncImage(
                model = movie.imageResId,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
//        AnimatedVisibility(visible = isSelected) {
//            Box(
//                modifier = Modifier.width(184.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    textAlign = TextAlign.Center,
//                    text = movie.movieName,
//                    style = TextStyle(
//                        fontSize = 16.sp,
//                        fontFamily = bodyFontFamily,
//                        fontWeight = FontWeight(400),
//                        color = Color(0xFFAED5FF),
//                    ),
//                    modifier = Modifier
//                        .border(
//                            width = 2.dp,
//                            color = Color(0xFFAED5FF),
//                            shape = RoundedCornerShape(16.dp)
//                        ).padding(vertical = 4.dp, horizontal = 16.dp)
//                )
//            }
//        }
    }
}
