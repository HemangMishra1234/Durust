package com.tripod.durust.presentation.datacollection

import Picker
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripod.durust.R
import rememberPickerState

@Composable
fun HeightPickerCard() {
    val heights = remember {
        listOf(
            "3'0", "3'1", "3'2", "3'3", "3'4", "3'5", "3'6", "3'7", "3'8", "3'9", "3'10", "3'11",
            "4'0", "4'1", "4'2", "4'3", "4'4", "4'5", "4'6", "4'7", "4'8", "4'9", "4'10", "4'11",
            "5'0", "5'1", "5'2", "5'3", "5'4", "5'5", "5'6", "5'7", "5'8", "5'9", "5'10", "5'11",
            "6'0", "6'1", "6'2", "6'3", "6'4", "6'5", "6'6", "6'7", "6'8", "6'9", "6'10", "6'11",
            "7'0", "7'1", "7'2", "7'3", "7'4", "7'5", "7'6", "7'7", "7'8", "7'9", "7'10", "7'11",
            "8'0", "8'1", "8'2", "8'3", "8'4", "8'5", "8'6", "8'7", "8'8", "8'9", "8'10", "8'11",
            "9'0", "9'1", "9'2", "9'3", "9'4", "9'5", "9'6", "9'7", "9'8", "9'9", "9'10", "9'11",
            "10'0", "10'1", "10'2", "10'3", "10'4", "10'5", "10'6", "10'7", "10'8", "10'9", "10'10", "10'11",
            "11'0"
        )

    }
    val heightState = rememberPickerState()

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .width(246.99998.dp)
            .height(466.94345.dp)
            .shadow(5.dp, shape = RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.weight(1f)
            ) {
                // Replace with your own illustration composable or image
                Spacer(modifier = Modifier.height(100.dp)) // Placeholder for illustration
                Image(
                    painter = painterResource(id = R.drawable.heightman),
                    contentDescription = "Height illustration",
                    modifier = Modifier
                        .width(123.93639.dp)
                        .padding(18.dp,0.dp,0.dp,0.dp)
                        .height(295.00354.dp)
                ) // Placeholder for illustration
            }
            Picker(
                items = heights,
                state = heightState,
                visibleItemsCount = 17,
                textStyle = TextStyle(fontSize = 18.sp),
                modifier = Modifier.weight(1f),
                textModifier = Modifier.height(28.dp)
            )
            Image(painter = painterResource(id = R.drawable.heightpointer),
                contentDescription = "Height illustration")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeightPickerCardPreview() {
    HeightPickerCard()
}
