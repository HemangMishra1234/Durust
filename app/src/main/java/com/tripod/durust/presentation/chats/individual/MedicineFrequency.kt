package com.tripod.durust.presentation.chats.individual

import android.util.Log
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

enum class MedicineFrequency(val displayName: String) {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday")
}

@Composable
fun MedicineFrequencyCard(day: MedicineFrequency, isSelected: Boolean, onClick: () -> Unit) {
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
                text = day.displayName,
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
fun MedicineFrequencyList(
    isEnabled: Boolean,
    selectedDays: List<MedicineFrequency> = emptyList(),
    onDaySelected: (List<MedicineFrequency>) -> Unit = {}
) {
    var selectedDaysState by remember { mutableStateOf(selectedDays) }

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
        MedicineFrequency.values().forEach { day ->
            MedicineFrequencyCard(
                day = day,
                isSelected = day in selectedDaysState,
                onClick = {
                    if (isEnabled) {
                        if (day in selectedDaysState)
                            selectedDaysState = selectedDaysState - day
//                            selectedDaysState.remove(day)
                        else
                            selectedDaysState = selectedDaysState+ day
//                            selectedDaysState.add(day)
                        onDaySelected(selectedDaysState)
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMedicineFrequencyList() {
    MedicineFrequencyList(
        isEnabled = true,
        selectedDays = listOf(MedicineFrequency.MONDAY, MedicineFrequency.WEDNESDAY),
        onDaySelected = {
            Log.i("MedicineFrequencyList", "Selected days: $it")
        }
    )
}