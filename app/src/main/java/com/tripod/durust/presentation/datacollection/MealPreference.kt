package com.tripod.durust.presentation.datacollection

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tripod.durust.R
import com.tripod.durust.ui.theme.bodyFontFamily


@Composable
fun MealPreferenceSelectionUI(initialValue: MealPreferenceEntity, isEnabled: Boolean, onTypeSelected: (MealPreferenceEntity) -> Unit) {
    val selectedType = remember { mutableStateOf<MealPreferenceEntity>(initialValue) }
    Column {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .width(323.81445.dp)
            .height(70.dp)
    ) {
        MealPreferenceEntity.entries.forEach { type ->
            val isSelected = type == selectedType.value
            Box(
                modifier = Modifier
                    .clickable {if(isEnabled) selectedType.value = type }
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFEEF6F8))
                    .animateContentSize()
                    .width(if (isSelected) 160.dp else 75.dp)
                    .height(70.dp)
                    .border(if (isSelected) 1.dp else 0.dp, Color.Gray, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                    AsyncImage(
                        model = type.imageResId,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        contentScale = ContentScale.Crop
                    )
            }
        }
    }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(322.dp)
                .height(35.dp)
                .background(color = Color(0xFFEEF6F8), shape = RoundedCornerShape(size = 12.dp))
        ) {
            Text(
                text = selectedType.value.displayName,
                style = TextStyle(
                    fontSize = 11.88.sp,
                    fontFamily = bodyFontFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF454547),
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MealPreferenceSelectionUIPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF7788F4)),
        contentAlignment = Alignment.Center
    ) {
        MealPreferenceSelectionUI(
            initialValue = MealPreferenceEntity.VEGETARIAN,
            isEnabled = false,
            onTypeSelected = { /* Handle onTypeSelected */ }
        )
    }
}

enum class MealPreferenceEntity(val displayName: String, val imageResId: Int) {
    VEGETARIAN("Vegetarian", R.drawable.vegetarianicon),
    NON_VEGETARIAN("Non-Vegetarian", R.drawable.nonvegicon),
    EGGETARIAN("Eggetarian", R.drawable.eggiterianicon)
}
