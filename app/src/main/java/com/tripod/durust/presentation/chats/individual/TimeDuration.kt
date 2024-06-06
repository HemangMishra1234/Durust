package com.tripod.durust.presentation.chats.individual

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripod.durust.presentation.datacollection.DatePicker
import com.tripod.durust.presentation.datacollection.HeadingCard
import com.tripod.durust.presentation.datacollection.rememberDatePickerState
import com.tripod.durust.ui.theme.bodyFontFamily

data class TimeDuration(val hours: Int, val minutes: Int)

@Composable
fun TimeDurationCard(
    initialDuration: TimeDuration,
    isEnabled: Boolean,
    onDurationChanged: (TimeDuration) -> Unit
) {
    val hours = remember { (0..23).map { it.toString().padStart(2, '0') } }
    val minutes = remember { (0..59).map { it.toString().padStart(2, '0') } }

    val hourState = rememberDatePickerState()
    val minuteState = rememberDatePickerState()

    LaunchedEffect(key1 = hourState.selectedItem, key2 = minuteState.selectedItem) {
        try {
            onDurationChanged(TimeDuration(hourState.selectedItem.toInt() + 1, minuteState.selectedItem.toInt() + 1))
        } catch (_: Exception) {
        }
    }

    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF7788F4),
        ),
        modifier = Modifier
            .shadow(
                elevation = 3.dp,
                spotColor = Color.Black,
                ambientColor = Color(0x40000000),
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                BorderStroke(width = 0.75.dp, color = Color(0xFFEEF6F8)),
                shape = RoundedCornerShape(size = 10.dp)
            )
            .width(316.dp)
            .height(198.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                HeadingCard(text = "Hours")
                HeadingCard(text = "Minutes")
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .shadow(
                        elevation = 5.dp,
                        spotColor = Color(0x40000000),
                        ambientColor = Color(0x40000000)
                    )
                    .width(300.dp)
                    .height(145.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(size = 9.dp))
            ) {
                if (isEnabled) {
                    DatePicker(
                        items = hours,
                        state = hourState,
                        visibleItemsCount = 3,
                        textStyle = TextStyle(fontSize = 15.sp),
                        modifier = Modifier.weight(1f),
                        startIndex = initialDuration.hours,
                        isEnabled = isEnabled
                    )
                    DatePicker(
                        modifier = Modifier.weight(1f),
                        items = minutes,
                        state = minuteState,
                        startIndex = initialDuration.minutes,
                        visibleItemsCount = 3,
                        textStyle = TextStyle(fontSize = 15.sp),
                        isEnabled = isEnabled
                    )
                } else {
                    Text(
                        text = initialDuration.hours.toString().padStart(2, '0'),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = bodyFontFamily,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF454547)
                        ),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = initialDuration.minutes.toString().padStart(2, '0'),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = bodyFontFamily,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF454547)
                        ),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTimeDurationCard() {
    TimeDurationCard(
        initialDuration = TimeDuration(2, 30),
        isEnabled = false,
        onDurationChanged = {
            Log.i("TimeDuration", "Duration changed to $it")
        }
    )
}