package com.tripod.durust.presentation.chats.individual

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripod.durust.R
import com.tripod.durust.ui.theme.bodyFontFamily

enum class StatsEntity(val displayName: String) {
    WEIGHT("Weight"),
    BLOOD_PRESSURE("Blood Pressure"),
    GLUCOSE("Glucose")
}

@Composable
private fun StatsCard(
    stat: StatsEntity,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor =
        when (stat) {
            StatsEntity.WEIGHT -> Color(0xFF9BDBFF)
            StatsEntity.BLOOD_PRESSURE -> Color(0xFFFF9BF5)
            StatsEntity.GLUCOSE -> Color(0xFFFDDC9E)
        }

    val imageResId = when (stat) {
        StatsEntity.WEIGHT -> R.drawable.destatsweighingmachine
        StatsEntity.BLOOD_PRESSURE -> R.drawable.destatsbloodpressure
        StatsEntity.GLUCOSE -> R.drawable.destatsglucode
    }
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(0.7317.dp)
            .width(if (!isSelected)103.05898.dp else 120.dp)
            .height(if(!isSelected)125.14806.dp else 150.dp)
            .clickable { onClick() }
            .border(if (isSelected) 2.dp else 0.dp, Color(0xFF151F55), RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier
                .background(backgroundColor) // Replace with your desired background color
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stat.displayName,
                style = TextStyle(
                    fontSize = 9.sp,
                    lineHeight = 15.46.sp,
                    fontFamily = bodyFontFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF454547),
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Replace with your desired icon or image
            Icon(
                painter = painterResource(id = imageResId),
                tint = Color(0xFF645935),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
        }
    }
}

@Composable
fun DEStatsListBase(selectedStatIn: StatsEntity? = null, isClickable: Boolean, onClick: (selectedStat: StatsEntity?) -> Unit) {
    var selectedStat by remember { mutableStateOf(selectedStatIn) }
    Row(
        modifier = Modifier
            .shadow(
                elevation = 3.5.dp,
                spotColor = Color(0x40000000),
                ambientColor = Color(0x40000000)
            )
            .border(
                width = 1.dp,
                color = Color(0xFFEEF6F8),
                shape = RoundedCornerShape(size = 12.5.dp)
            )
            .width(360.dp)
            .height(153.dp)
            .padding(start = 9.dp, top = 0.49998.dp, end = 8.70682.dp, bottom = 0.37389.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatsEntity.values().forEach { stat ->
            StatsCard(
                stat = stat,
                isSelected = selectedStat == stat,
                onClick = {
                    if (isClickable) {
                        selectedStat = stat
                        onClick(selectedStat)
                    }
                }
            )
            Spacer(modifier = Modifier.width(9.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStatsSelection() {
    DEStatsListBase(
        selectedStatIn = StatsEntity.WEIGHT,
        isClickable = true,
        onClick = { /* Handle click */ }
    )
}