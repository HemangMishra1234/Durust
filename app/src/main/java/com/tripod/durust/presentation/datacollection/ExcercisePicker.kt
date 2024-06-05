package com.tripod.durust.presentation.datacollection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun ExercisePicker() {
    val exercises = listOf(
        "Exercise 1", "Exercise 2", "Exercise 3", "Exercise 4"
    )
    val pagerState = rememberPagerState(){
        exercises.size
    }
    var selectedExerciseIndex by remember { mutableStateOf(0) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF6887FE))
            .padding(16.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .border(
                    width = 0.72848.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(size = 9.10596.dp)
                )
                .width(220.dp)
                .height(175.56291.dp)
                .padding(8.01324.dp),
            verticalAlignment = Alignment.CenterVertically
        ) { page ->
            ExerciseCard(
                exercise = exercises[page],
                isSelected = page == selectedExerciseIndex,
                onClick = {
                    selectedExerciseIndex = page
                }
            )
        }
    }
}

@Composable
fun ExerciseCard(
    exercise: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(size = 9.10596.dp),
        border = BorderStroke(0.5.dp, Color.Gray),
        modifier = Modifier
            .width(104.97351.dp)
            .height(134.53642.dp)
            .padding(
                top = if (isSelected) 0.dp else 13.84106.dp,
                bottom = if (isSelected) 0.dp else 11.65563.dp
            )
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(9.10596.dp))
            .background(Color.White)
            .clickable { onClick() }
            .zIndex(if (isSelected) 1f else 0f)
            .shadow(if (isSelected) 8.dp else 0.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = exercise,
                fontSize = 24.sp,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImagePagerPreview() {
    ExercisePicker()
}
