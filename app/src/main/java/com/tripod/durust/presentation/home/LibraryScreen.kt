package com.tripod.durust.presentation.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.tripod.durust.presentation.home.individuals.BookRecommendations
import com.tripod.durust.presentation.home.individuals.Movie
import com.tripod.durust.presentation.home.individuals.MovieRecommendations
import com.tripod.durust.presentation.home.individuals.MoviesCarouselBase
import com.tripod.durust.presentation.home.individuals.MusicRecommendations
import com.tripod.durust.presentation.home.individuals.PodcastRecommendations

@Composable
fun LibraryScreen(navController: NavController) {
    val scroll = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scroll)
            .fillMaxWidth()
            .padding(bottom = 100.dp)
    ) {
        Surface(
            color = Color(0xFFBBDEFB),
            shape = RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp),
            modifier = Modifier.fillMaxWidth()

        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 32.dp)
                    .fillMaxWidth()
                    .statusBarsPadding()
            ) {
                Text(
                    text = "Library",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "All things wellness at your fingertips. This is what our community recommends for you",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
        MovieRecommendations()
        MusicRecommendations()
        BookRecommendations()
        PodcastRecommendations()
    }
}

@Preview(showBackground = true)
@Composable
fun LibraryHeaderPreview() {
//    LibrarySr()
}




