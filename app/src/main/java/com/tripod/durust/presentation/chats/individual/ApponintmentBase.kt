package com.tripod.durust.presentation.chats.individual

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripod.durust.data.DateEntity
import com.tripod.durust.presentation.datacollection.TimeEntity
import com.tripod.durust.presentation.datacollection.getTime
import com.tripod.durust.presentation.home.individuals.DoctorProfession
import com.tripod.durust.ui.theme.bodyFontFamily

enum class AppointmentMenu(val displayName: String) {
    ADD_APPOINTMENT("Add appointment"),
    UPCOMING_APPOINTMENT("Upcoming appointment"),
    APPOINTMENT_HISTORY("Appointment history")
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppointmentMenuBase(
    isEnabled: Boolean,
    initialSelectedOption: AppointmentMenu?,
    onOptionSelected: (AppointmentMenu) -> Unit
) {
    var selectedOption by remember { mutableStateOf(initialSelectedOption) }
    FlowRow(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AppointmentMenu.values().forEach { option ->
            AppointmentMenuItem(
                option = option,
                isSelected = option == selectedOption,
                isEnabled = isEnabled,
                onOptionSelected = {
                    selectedOption = it
                    onOptionSelected(it)
                }
            )
        }
    }
}

@Composable
private fun AppointmentMenuItem(
    option: AppointmentMenu,
    isSelected: Boolean,
    isEnabled: Boolean,
    onOptionSelected: (AppointmentMenu) -> Unit
) {
    Box(modifier = Modifier
        .clip(RoundedCornerShape(16.dp))
        .background(Color(0xFFEEF6F8))
        .animateContentSize()
        .clickable { if (isEnabled) onOptionSelected(option) }
        .border(
            border = BorderStroke(
                if (isSelected) 1.dp else 0.dp, Color.Gray
            ), shape = RoundedCornerShape(16.dp)
        ))
    {
        Text(
            text = option.displayName,
            style = TextStyle(
                fontSize = if (isSelected) 14.sp else 12.sp,
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight(400),
                color = Color(0xFF454547),
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}
