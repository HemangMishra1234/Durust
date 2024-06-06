package com.tripod.durust.presentation.datacollection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.tripod.durust.data.DateEntity
import com.tripod.durust.ui.theme.bodyFontFamily
import java.util.Calendar
import java.util.TimeZone

fun utcToDateEntity(utc: Long): DateEntity {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = utc
    }
    return DateEntity(
        day = calendar.get(Calendar.DAY_OF_MONTH),
        month = calendar.get(Calendar.MONTH) + 1, // Calendar.MONTH is zero-based
        year = calendar.get(Calendar.YEAR)
    )
}

fun dateEntityToMillis(dateEntity: DateEntity): Long {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    calendar.set(Calendar.YEAR, dateEntity.year)
    calendar.set(Calendar.MONTH, dateEntity.month - 1) // Calendar.MONTH is zero-based
    calendar.set(Calendar.DAY_OF_MONTH, dateEntity.day)
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

@Preview(showBackground = true)
@Composable
fun DatePickerCardPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF7788F4)),
        contentAlignment = Alignment.Center
    ) {
        DatePickerCard(
            initialDate = DateEntity(year = 1990, month = 1, day = 1),
            isEnabled = true,
            onDateChanged = {}
        )
//        HeightPickerCard()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerCard(
    initialDate: DateEntity,
    isEnabled: Boolean,
    onDateChanged: (DateEntity) -> Unit
) {
    val datePickerState = rememberDatePickerState(dateEntityToMillis(initialDate))

    LaunchedEffect(key1 = datePickerState.selectedDateMillis) {
        try {
            if (datePickerState.selectedDateMillis != null)
                onDateChanged(utcToDateEntity(datePickerState.selectedDateMillis!!))
        } catch (_: Exception) {
        }
    }

    Card(
        shape = RoundedCornerShape(10.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = Color(0xFF7788F4),
//        ),
        modifier = Modifier
            .shadow(
                elevation = 3.3796792030334473.dp,
                spotColor = Color.Black,
                ambientColor = Color(0x40000000),
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                BorderStroke(width = 0.75598.dp, color = Color(0xAAEFF6F8)),
                shape = RoundedCornerShape(size = 10.dp)
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(16.dp)
        ) {
            AnimatedVisibility(isEnabled) {
                DatePicker(
                    state = datePickerState,
                    modifier = Modifier,
                    colors = DatePickerDefaults.colors(
                        containerColor = Color.White,
                    )
                )
            } 
            AnimatedVisibility(visible = !isEnabled){
                Text(
                    text = "${initialDate.day.toString().padStart(2, '0')}/" +
                            "${initialDate.month.toString().padStart(2, '0')}/" +
                            initialDate.year.toString().padStart(4, '0'),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = bodyFontFamily,
                        fontWeight = FontWeight(400),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }

    @Composable
    fun HeadingCard(text: String, onClick: () -> Unit = {}) {
        Card(
            onClick = onClick,
            shape = RoundedCornerShape(size = 9.44976.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFAED5FF),
            ),
            modifier = Modifier
                .width(80.dp)
                .height(26.45933.dp)
                .shadow(
                    4.dp,
                    spotColor = Color.Black,
                    ambientColor = Color.Black,
                    shape = RoundedCornerShape(9.44976.dp)
                ), elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = text,
                    style = TextStyle(
                        fontSize = 15.12.sp,
                        fontFamily = bodyFontFamily,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF454547),
                    )
                )
            }
        }
    }
}