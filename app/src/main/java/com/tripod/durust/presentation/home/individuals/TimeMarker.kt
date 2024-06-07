package com.tripod.durust.presentation.home.individuals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TimeMarkers() {
    val currentTime = LocalTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val currentHour = currentTime.hour

    val timesList = List(5) { currentHour - 2 + it }.map { hour ->
        val validHour = hour % 24
        currentTime.withHour(validHour).format(formatter)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalDivider(
            color = Color(0xFFFB8A7A),
            thickness = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            timesList.forEachIndexed { index, time ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    VerticalDivider(
                        color = Color(0xFFFB8A7A),
                        thickness = 2.dp,
                        modifier = Modifier
                            .height(30.dp)
                            .width(2.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TimeMarker(
                        time = time,
                        backgroundColor = if (index == 2) Color(0xFFFF8A80) else Color(0xFFBBDEFB),
                        textColor = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun TimeMarker(time: String, backgroundColor: Color, textColor: Color) {
    Surface(
        modifier = Modifier
            .width(60.dp)
            .height(50.dp),
        shape = RoundedCornerShape(24.dp),
        color = backgroundColor
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = time,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimeMarkersPreview() {
    TimeMarkers()
}
