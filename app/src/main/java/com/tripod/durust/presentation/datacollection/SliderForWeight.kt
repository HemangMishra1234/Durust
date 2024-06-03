package com.tripod.durust.presentation.datacollection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripod.durust.R
import java.time.format.TextStyle
import kotlin.math.roundToInt

@Composable
fun DaysSlider() {
    var sliderPosition by remember { mutableStateOf(0f) }
    val sliderRange = 0f..7f

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Color(0xFF6887FE), RoundedCornerShape(8.dp))
            .padding(8.dp)
            .width(200.dp)
            .height(123.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            var sliderWidth by remember { mutableStateOf(0) }

            Box(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .offset {
                        IntOffset(
                            x = ((sliderPosition - sliderRange.start) / (sliderRange.endInclusive - sliderRange.start) * sliderWidth).roundToInt(),
                            y = 0
                        )
                    }
//                    .background(Color.White, RoundedCornerShape(8.dp))
//                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Image(painterResource(id = R.drawable.pointer),contentDescription = null)
                Text(
                    text = sliderPosition.roundToInt().toString(),
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                )
            }
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                valueRange = sliderRange,
                steps = 7,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .onGloballyPositioned { layoutCoordinates ->
                        sliderWidth = layoutCoordinates.size.width
                    },
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color(0xFFB3C7FE),
                    inactiveTrackColor = Color(0xFFB3C7FE)
                )
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .background(Color(0xFFB3C7FE), RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "${sliderPosition.roundToInt()} Days",
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DaysSliderPreview() {
    DaysSlider()
}
