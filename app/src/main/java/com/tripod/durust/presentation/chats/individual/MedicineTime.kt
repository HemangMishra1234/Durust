package com.tripod.durust.presentation.chats.individual

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.tripod.durust.ui.theme.bodyFontFamily

enum class MedicineTime(val displayName: String) {
    ON_EMPTY_STOMACH("On Empty Stomach"),
    BEFORE_BREAKFAST("Before Breakfast"),
    AFTER_BREAKFAST("After Breakfast"),
    BEFORE_LUNCH("Before Lunch"),
    AFTER_LUNCH("After Lunch"),
    BEFORE_DINNER("Before Dinner"),
    AFTER_DINNER("After Dinner")
}

@Composable
fun MedicineTimeCard(time: MedicineTime, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFB0D0F0) else Color(0xFFEEF6F8)
        ),
        shape = RoundedCornerShape(size = 9.4112.dp),
        modifier = Modifier
            .shadow(
                elevation = 4.dp,
                spotColor = Color(0x40000000),
                ambientColor = Color(0x40000000)
            )
            .width(175.42471.dp)
            .height(31.05791.dp)
            .background(color = Color(0xFFEEF6F8), shape = RoundedCornerShape(size = 9.4112.dp))
            .clickable { onClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = time.displayName,
                style = TextStyle(
                    fontSize = 12.05.sp,
                    lineHeight = 15.81.sp,
                    fontFamily = bodyFontFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF454547),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}

@Composable
fun MedicineTimeList(
    isEnabled: Boolean,
    selectedTime: MedicineTime? = null,
    onTimeSelected: (MedicineTime?) -> Unit = {}
) {
    var selectedTimeState by remember { mutableStateOf(selectedTime) }

    Column(
        verticalArrangement = Arrangement.spacedBy(9.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .shadow(
                elevation = 4.dp,
                spotColor = Color(0x40000000),
                ambientColor = Color(0x40000000)
            )
            .border(
                width = 0.7491.dp,
                color = Color(0xFFEEF6F8),
                shape = RoundedCornerShape(size = 9.3638.dp)
            )
            .width(191.0215.dp)
            .height(210.34409.dp)
            .padding(8.24014.dp)
    ) {
        MedicineTime.values().forEach { time ->
            MedicineTimeCard(
                time = time,
                isSelected = time == selectedTimeState,
                onClick = {
                    if (isEnabled) {
                        if (time == selectedTimeState)
                            selectedTimeState = null
                        else
                            selectedTimeState = time
                        onTimeSelected(selectedTimeState)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMedicineTimeList() {
    MedicineTimeList(
        isEnabled = false,
        selectedTime = MedicineTime.BEFORE_BREAKFAST,
        onTimeSelected = { /* Handle selection */ }
    )
}