package com.tripod.durust.presentation.datacollection

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripod.durust.ui.theme.bodyFontFamily

@Composable
fun StepsInputSelectionUI(initialValue: StepsInputEntity,isEnabled :Boolean, onTypeSelected: (StepsInputEntity) -> Unit) {
    val selectedType = remember { mutableStateOf<StepsInputEntity>(initialValue) }
    val entries = if(isEnabled) StepsInputEntity.entries else listOf(initialValue)
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .width(if(isEnabled)323.81445.dp else 170.dp)
            .height(70.dp)
    ) {
        entries.forEach { type ->
        val isSelected = type == selectedType.value
        Box(modifier =
        Modifier.clickable { selectedType.value = type
        onTypeSelected(type)}
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .animateContentSize()
            .width(if(isSelected) 150.dp else 70.dp)
            .height(70.dp)
            .border(if(isSelected) 1.dp else 0.dp, Color.Gray, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center) {
                Text(
                    text = type.displayName,
                    style = TextStyle(
                        fontSize = if(isSelected) 11.88.sp else 8.sp,
                        fontFamily = bodyFontFamily,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF454547),
                        ),
                    modifier = Modifier

                )

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun StepsInputSelectionUIPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF7788F4)),
        contentAlignment = Alignment.Center
    ) {
        StepsInputSelectionUI(
            initialValue = StepsInputEntity.LESS_THAN_FIVE_THOUSAND,
            isEnabled = true,
            onTypeSelected = { /* Handle onTypeSelected */ }
        )
    }
}


enum class StepsInputEntity(val displayName: String) {
    LESS_THAN_FIVE_THOUSAND("<5,000"),
    FIVE_THOUSAND_TO_TEN_THOUSAND("5,000-10,000"),
    GREATER_THAN_TEN_THOUSAND(">10,000")
}