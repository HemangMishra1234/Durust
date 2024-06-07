package com.tripod.durust.presentation.chats.individual


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tripod.durust.R
import com.tripod.durust.ui.theme.bodyFontFamily
import kotlin.math.absoluteValue

@Preview(showBackground = true)
@Composable
fun PreviewCarouselView() {
    // Replace with your actual image resource IDs
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF7788F4)),
        contentAlignment = Alignment.Center) {

        DataEntryCarouselBase(
            inactiveValue = DataEntryCarouselEntity.FOOD,
            isActive = true,
            modifier = Modifier
                .width(280.dp)
                .height(250.dp)
        ) {

        }
    }
}

@Composable
fun DataEntryCarouselBase(inactiveValue: DataEntryCarouselEntity, modifier: Modifier, isActive: Boolean, onDataEntrySelected: (DataEntryCarouselEntity) -> Unit) {
    val dataEntries = if(isActive)DataEntryCarouselEntity.values().toList() else listOf(inactiveValue)
    val pagerState = rememberPagerState(){dataEntries.size}
    var selectedDataEntry = dataEntries[pagerState.currentPage]

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
            ImageCard(imageNumber = dataEntries[page], isSelected = (selectedDataEntry == dataEntries[page]), modifier = Modifier)
        }
    }

    // Automatically select the center image
    LaunchedEffect(pagerState.currentPage) {
        selectedDataEntry = dataEntries[pagerState.currentPage]
        onDataEntrySelected(selectedDataEntry)
    }
}

@Composable
fun ImageCard(imageNumber: DataEntryCarouselEntity, isSelected: Boolean, modifier: Modifier) {
    Column {
        Box(modifier = Modifier
            .width(if (isSelected) 144.57143.dp else 144.dp)
            .height(if (isSelected) (184.dp) else 160.dp)
            .animateContentSize()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)) {
            AsyncImage(
                model = imageNumber.imageId,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxSize()

            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        AnimatedVisibility(visible = isSelected) {
            Box(
                modifier = Modifier.width(184.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = imageNumber.displayName,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = bodyFontFamily,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFAED5FF),
                    ),
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = Color(0xFFAED5FF),
                            shape = RoundedCornerShape(16.dp)
                        ).padding(vertical = 4.dp, horizontal = 16.dp)
                )
            }
        }

    }
}
fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (start * (1 - fraction) + stop * fraction)
}


enum class DataEntryCarouselEntity(val displayName: String, val imageId: Int) {
    FOOD("Food", R.drawable.food7),
    EXERCISE("Exercise", R.drawable.deexcercise),
    SLEEP("Sleep", R.drawable.desleep),
    MEDICINE("Medicine", R.drawable.demedicine),
    YOUR_STATS("Your Stats", R.drawable.deyourstats),
    WATER("Water", R.drawable.dewatericon)
}