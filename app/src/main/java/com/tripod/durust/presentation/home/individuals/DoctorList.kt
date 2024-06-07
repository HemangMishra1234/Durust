package com.tripod.durust.presentation.home.individuals

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tripod.durust.R
import com.tripod.durust.ui.theme.bodyFontFamily
import kotlin.math.absoluteValue

enum class Doctor(val doctorName: String, val profession: DoctorProfession, val resId: Int) {
    DR_ROHAN("Dr Rohan", DoctorProfession.ENDOCRINOLOGIST, R.drawable.drrohanpng),
    DR_MEERA("Dr Meera", DoctorProfession.CARDIOLOGIST, R.drawable.drmeerapng),
    DR_RUNDRA("Dr Rundra", DoctorProfession.PHYSICIAN, R.drawable.drrudrapng),
    DR_ABEER("Dr Abeer", DoctorProfession.NUTRITIONIST, R.drawable.drabeerpng)
}

@Composable
fun DoctorRecommendations() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Doctor recommendations",
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
            DoctorsListCarouselBase(
                inactiveDoctor = Doctor.DR_ROHAN,
                modifier = Modifier,
                isActive = true
            ) {

            }
        }
    }
}

@Composable
fun DoctorRecommendationsPreview() {
    DoctorRecommendations()
}


@Composable
fun DoctorsListCarouselBase(
    inactiveDoctor: Doctor,
    modifier: Modifier,
    isActive: Boolean,
    onDoctorSelected: (Doctor) -> Unit
) {
    val doctors = if (isActive) Doctor.values().toList() else listOf(inactiveDoctor)
    val pagerState = rememberPagerState() { doctors.size }
    var selectedDoctor = doctors[pagerState.currentPage]

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
            DoctorCard(
                doctor = doctors[page],
                isSelected = (selectedDoctor == doctors[page]),
                modifier = Modifier
            )
        }
    }

    // Automatically select the center doctor
    LaunchedEffect(pagerState.currentPage) {
        selectedDoctor = doctors[pagerState.currentPage]
        onDoctorSelected(selectedDoctor)
    }
}

@Composable
fun DoctorCard(doctor: Doctor, isSelected: Boolean, modifier: Modifier) {
    Column {
        Box(
            modifier = Modifier
                .width(if (isSelected) 144.57143.dp else 144.dp)
                .height(if (isSelected) (184.dp) else 160.dp)
                .animateContentSize()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
        ) {
            AsyncImage(
                model = doctor.resId,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxSize()
            )
        }
//        Spacer(modifier = Modifier.height(8.dp))
//        AnimatedVisibility(visible = isSelected) {
//            Box(
//                modifier = Modifier.width(184.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    textAlign = TextAlign.Center,
//                    text = doctor.doctorName,
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
//                        )
//                        .padding(vertical = 4.dp, horizontal = 16.dp)
//                )
//            }
//        }
    }
}


fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (start * (1 - fraction) + stop * fraction)
}