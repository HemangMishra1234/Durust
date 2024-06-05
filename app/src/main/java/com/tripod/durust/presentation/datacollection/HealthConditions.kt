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
fun HealthConditionList(selectedCondition: HealthCondition? = null, onConditionSelected: (HealthCondition?) -> Unit = {}) {
    var selectedConditionState by remember { mutableStateOf(selectedCondition) }

    Column(
        verticalArrangement = Arrangement.spacedBy(9.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .shadow(
                elevation = 3.3.dp,
                spotColor = Color(0x40000000),
                ambientColor = Color(0x40000000)
            )
            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(size = 12.5.dp))
            .width(192.dp)
            .height(368.36295.dp)
            .padding(8.28185.dp)
    ) {
        HealthCondition.entries.forEach { condition ->
            HealthConditionCard(
                condition = condition,
                isSelected = condition == selectedConditionState,
                onClick = {
                    if(condition == selectedConditionState)
                        selectedConditionState = null
                    else
                        selectedConditionState = condition
                    onConditionSelected(selectedConditionState)
                }
            )
        }
    }
}

@Composable
fun HealthConditionCard(condition: HealthCondition, isSelected: Boolean, onClick: () -> Unit) {
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
                text = condition.displayName,
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
fun HealthConditionListPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF7788F4)),
        contentAlignment = Alignment.Center
    ) {
        HealthConditionList()
    }
}

enum class HealthCondition(val displayName: String) {
    OBESITY("Obesity"),
    LOW_BLOOD_PRESSURE("Low Blood Pressure"),
    HIGH_BLOOD_PRESSURE("High Blood Pressure"),
    PREDIABETIC("Prediabetic"),
    HEART_DISEASE("Heart Disease"),
    DIABETIC_T1("Diabetic T1"),
    DIABETIC_T2("Diabetic T2"),
    PCOD_PCOS("PCOD/PCOS"),
    OTHER("Other")
}
