//package com.tripod.durust.presentation
//
//import PageOne
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.geometry.Rect
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.geometry.center
//import androidx.compose.ui.graphics.Outline
//import androidx.compose.ui.graphics.Path
//import androidx.compose.ui.graphics.Shape
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.unit.Density
//import androidx.compose.ui.unit.LayoutDirection
//import com.tripod.durust.BakingScreen
//import kotlin.math.sqrt
//
//@Composable
//fun CirclePager() {
//    val state = rememberPagerState(initialPage = 0){2}
//    HorizontalPager(
//        state = state,
//        modifier = Modifier.graphicsLayer {
//            // MAKE THE PAGE NOT MOVE
//            val pageOffset = state.offsetForPage(page)
//            translationX = size.width * pageOffset
//
//            // ADD THE CIRCULAR CLIPPING
//            val endOffset = state.endOffsetForPage(page)
//
//            shadowElevation = 20f
//            shape = CirclePath(
//                progress = 1f - endOffset.absoluteValue,
//                origin = Offset(
//                    size.width,
//                    offsetY,
//                )
//            )
//            clip = true
//
//            // PARALLAX SCALING
//            val absoluteOffset = state.offsetForPage(page).absoluteValue
//            val scale = 1f + (absoluteOffset.absoluteValue * .4f)
//
//            scaleX = scale
//            scaleY = scale
//
//            // FADE AWAY
//            val startOffset = state.startOffsetForPage(page)
//            alpha = (2f - startOffset) / 2f
//
//        }
//    ) { index->
//        when(index){
//            0 -> {
//                PageOne()
//            }
//            1 -> {
//                BakingScreen()
//            }
//        }
//
//    }
//}
//
//class CirclePath(private val progress: Float, private val origin: Offset = Offset(0f, 0f)) : Shape {
//    override fun createOutline(
//        size: Size, layoutDirection: LayoutDirection, density: Density
//    ): Outline {
//
//        val center = Offset(
//            x = size.center.x - ((size.center.x - origin.x) * (1f - progress)),
//            y = size.center.y - ((size.center.y - origin.y) * (1f - progress)),
//        )
//        val radius = (sqrt(
//            size.height * size.height + size.width * size.width
//        ) * .5f) * progress
//
//        return Outline.Generic(Path().apply {
//            addOval(
//                Rect(
//                    center = center,
//                    radius = radius,
//                )
//            )
//        })
//    }
//}