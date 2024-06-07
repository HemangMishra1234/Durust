package com.tripod.durust.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ImageFrame(
    modifier: Modifier,
    imgRes: Int,
    width: Int = 132,
    height: Int = 162,
    onClick: () -> Unit
) {
    AsyncImage(
        model = imgRes,
        contentDescription = "image description",
        modifier = modifier
            .clip(shape = RoundedCornerShape(24.dp))
            .clickable { onClick() }
            .padding(1.dp)
            .width(width.dp)
            .height(height.dp)
    )
}