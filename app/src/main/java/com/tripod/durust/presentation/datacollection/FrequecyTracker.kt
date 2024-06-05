package com.tripod.durust.presentation.datacollection

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

@Composable
fun FrequencyList(
    isEnabled: Boolean,
    selectedFrequency: CheckUpFrequency? = null,
    onFrequencySelected: (CheckUpFrequency?) -> Unit = {}
) {
    var selectedFrequencyState by remember { mutableStateOf(selectedFrequency) }

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
        CheckUpFrequency.entries.forEach { frequency ->
            FrequencyCard(
                frequency = frequency,
                isSelected = frequency == selectedFrequencyState,
                onClick = {
                    if (isEnabled) {
                        if (frequency == selectedFrequencyState)
                            selectedFrequencyState = null
                        else
                            selectedFrequencyState = frequency
                        onFrequencySelected(selectedFrequencyState)
                    }
                }
            )
        }
    }
}

@Composable
fun FrequencyCard(frequency: CheckUpFrequency, isSelected: Boolean, onClick: () -> Unit) {
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
                text = frequency.displayName,
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

@Preview(showBackground = true)
@Composable
fun FrequencyListPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF7788F4)),
        contentAlignment = Alignment.Center
    ) {
//        FrequencyList()
    }
}


enum class CheckUpFrequency(val displayName: String) {
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly"),
    YEARLY("Yearly"),
    NEVER("Never")
}
