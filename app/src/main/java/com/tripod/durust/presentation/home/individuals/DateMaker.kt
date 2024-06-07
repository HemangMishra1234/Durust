package com.tripod.durust.presentation.home.individuals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DateMarkers() {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("d")
    val currentDay = currentDate.format(formatter).toInt()

    val daysList = List(5) { currentDay - 2 + it }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        daysList.forEachIndexed { index, day ->
            val isToday = index == 2
            DateMarker(
                number = day,
                backgroundColor = if (isToday) Color(0xFFFF8A80) else Color(0xFFBBDEFB),
                textColor = Color.Black
            )
        }
    }
}

@Composable
fun DateMarker(number: Int, backgroundColor: Color, textColor: Color) {
    Surface(
        modifier = Modifier
            .width(46.dp)
            .height(70.dp),
        shape = RoundedCornerShape(20.dp),
        color = backgroundColor
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DateMarkersPreview() {
    DateMarkers()
}
