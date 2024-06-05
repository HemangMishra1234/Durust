package com.tripod.durust.presentation.datacollection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripod.durust.R
import com.tripod.durust.ui.theme.bodyFontFamily
import kotlin.math.roundToInt

@Composable
fun DaysSlider(initalSliderPosition: Int, isEnabled: Boolean, onDaysChange: (Int) -> Unit) {
    var sliderPosition by remember { mutableStateOf(initalSliderPosition) }
    val sliderRange = 0f..7f

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp)
            .height(140.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            var sliderWidth by remember { mutableStateOf(0) }

            Box(
                contentAlignment = Alignment.Center,
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
                    text = sliderPosition.toString(),
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                )
            }
            Slider(
                value = sliderPosition.toFloat(),
                onValueChange = { sliderPosition = it.roundToInt()
                                onDaysChange(it.roundToInt())},
                valueRange = sliderRange,
                steps = 7,
                enabled = isEnabled,
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
                    .padding(top = 2.dp)
                    .background(Color(0xFFB3C7FE), RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {

                Text(
                    text = "${sliderPosition} Days",
                    style = TextStyle(
                        fontSize = 14.6.sp,
                        fontFamily = bodyFontFamily,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF454547),
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DaysSliderPreview() {
//    DaysSlider()
}
