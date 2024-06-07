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

enum class Book(val title: String, val imageResId: Int, val link: String) {
    BOOK1("What Happened to You?", R.drawable.bookwhathappenedtoyou, "https://www.google.com/search?q=What+Happened+to+You?"),
    BOOK2("The Gifts of Imperfection", R.drawable.booktheimperfections, "https://www.google.com/search?q=The+Gifts+of+Imperfection"),
    BOOK3("How to Do the Work", R.drawable.booktheworktodo, "https://www.google.com/search?q=How+to+Do+the+Work"),
    BOOK4("The Feeling Good Handbook", R.drawable.bookthefeelnggood, "https://www.google.com/search?q=The+Feeling+Good+Handbook"),
    BOOK5("The Happiness Trap", R.drawable.bookthehappinesstrap, "https://www.google.com/search?q=The+Happiness+Trap"),
    BOOK6("What My Bones Know", R.drawable.bookwhathappenedtoyou, "https://www.google.com/search?q=What+My+Bones+Know");
}

// Usage example:
fun main() {
    for (book in Book.values()) {
        println("Title: ${book.title}, ImageResId: ${book.imageResId}, Link: ${book.link}")
    }
}

@Composable
fun BookCard(book: Book, isSelected: Boolean, modifier: Modifier) {
    val context = LocalContext.current
    Column {
        Box(modifier = Modifier
            .clickable {
                val uri = Uri.parse(book.link)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            }
            .width(if (isSelected) 144.57143.dp else 144.dp)
            .height(if (isSelected) (184.dp) else 160.dp)
            .animateContentSize()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)) {
            AsyncImage(
                model = book.imageResId,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun BookCarouselBase(
    inactiveBook: Book,
    modifier: Modifier,
    isActive: Boolean,
    onBookSelected: (Book) -> Unit
) {
    val books = if (isActive) Book.values().toList() else listOf(inactiveBook)
    val pagerState = rememberPagerState() { books.size }
    var selectedBook = books[pagerState.currentPage]

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
            BookCard(
                book = books[page],
                isSelected = (selectedBook == books[page]),
                modifier = Modifier
            )
        }
    }

    // Automatically select the center book
    LaunchedEffect(pagerState.currentPage) {
        selectedBook = books[pagerState.currentPage]
        onBookSelected(selectedBook)
    }
}

@Composable
fun BookRecommendations() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Book recommendations",
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
            BookCarouselBase(
                inactiveBook = Book.BOOK1,
                modifier = Modifier,
                isActive = true
            ) {

            }
        }
    }
}


