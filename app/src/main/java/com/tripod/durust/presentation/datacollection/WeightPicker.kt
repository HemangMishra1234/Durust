package com.tripod.durust.presentation.datacollection


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripod.durust.ui.theme.bodyFontFamily
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map


@Preview(showBackground = true)
@Composable
fun WeightPickerCardPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF7788F4))) {
        WeightPickerCard(isEnabled = false, initialWeight = 62)
    }
}

@Composable
fun WeightPickerCard(initialWeight: Int = 0, isEnabled: Boolean,onWeightChanged: (Int) -> Unit = {}) {
    val hundredsState = rememberWeightPickerState()
    val tensState = rememberWeightPickerState()
    val unitsState = rememberWeightPickerState()
    val hundreds = initialWeight / 100
    val tens = (initialWeight % 100) / 10
    val units = initialWeight % 10

    val weight = remember {
        derivedStateOf {
            "${hundredsState.selectedItem}${tensState.selectedItem}${unitsState.selectedItem}".toIntOrNull() ?: 0
        }
    }

    LaunchedEffect(key1 = weight.value) {
        Log.i("WeightPickerCard", "Weight: ${weight.value}")
        onWeightChanged(weight.value)
    }

    Box(
        modifier = Modifier
            .shadow(
                elevation = 2.5292246341705322.dp,
                spotColor = Color(0x40000000),
                ambientColor = Color(0x40000000)
            )
            .border(
                width = 0.97278.dp,
                color = Color(0xFFEEF6F8),
                shape = RoundedCornerShape(size = 10.5.dp)
            )
            .width(240.dp)
            .height(105.00001.dp)
            .padding(
                start = 16.29311.dp,
                top = 21.72414.dp,
                end = 16.29311.dp,
                bottom = 21.72414.dp
            )) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier =Modifier.width(16.29.dp))
            WeightDigitPicker(state =hundredsState,
                startIndex = hundreds
            ,isEnabled = isEnabled)
            Spacer(modifier = Modifier.width(11.77.dp))
            WeightDigitPicker(state = tensState,
                startIndex = tens,
                isEnabled = isEnabled)
            Spacer(modifier = Modifier.width(11.77.dp))
            WeightDigitPicker(state = unitsState,
                startIndex = units,
                isEnabled = isEnabled)
            Spacer(modifier = Modifier.width(11.77.dp))
            Text(
                text = "Kg",
                style = TextStyle(
                    fontSize = 21.72.sp,
                    lineHeight = 47.97.sp,
                    fontFamily = bodyFontFamily,
                    fontWeight = FontWeight(600),
                    color = Color(0xFFEEF6F8),
                )
            )
            Spacer(modifier =Modifier.width(16.29.dp))
        }
    }
    Text("text = ${weight.value}")
}

@Composable
private fun WeightDigitPicker(modifier: Modifier = Modifier,
                              isEnabled: Boolean,
                              state: WeightPickerState, startIndex: Int = 0){
    val values = remember { (0..9).map { it.toString() } }
    Box(modifier = modifier
        .shadow(
            elevation = 3.8911149501800537.dp,
            spotColor = Color(0x40000000),
            ambientColor = Color(0x40000000)
        )
        .width(41.63793.dp)
        .height(62.44828.dp)
        .background(
            color = Color(0xFFFFFFFF),
            shape = RoundedCornerShape(size = 3.62069.dp)
        )
        .padding(start = 13.57789.dp, top = 7.24141.dp, end = 13.06004.dp),) {
        if(isEnabled)
        WeightPicker(
            modifier= Modifier.align(Alignment.Center),
            items = values,
            state = state,
            visibleItemsCount = 1,
            startIndex = startIndex,
            textStyle = TextStyle(
                fontSize = 21.sp,
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight(600),
                color = Color(0xFF454547),
            ),
        )
        else
            Text(
                text = startIndex.toString(),
                style = TextStyle(
                    fontSize = 21.sp,
                    fontFamily = bodyFontFamily,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF454547),
                ),
                modifier = Modifier.align(Alignment.Center)
            )
    }
}


@Composable
private fun WeightPicker(
    modifier: Modifier = Modifier,
    items: List<String>,
    state: WeightPickerState = rememberWeightPickerState(),
    startIndex: Int = 0,
    visibleItemsCount: Int = 3,
    textModifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
) {

    val visibleItemsMiddle = visibleItemsCount / 2
    val listScrollCount = Integer.MAX_VALUE
    val listScrollMiddle = listScrollCount / 2
    val listStartIndex = listScrollMiddle - listScrollMiddle % items.size - visibleItemsMiddle + startIndex

    fun getItem(index: Int) = items[index % items.size]

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = listStartIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val itemHeightPixels = remember { mutableIntStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPixels.value)

    val fadingEdgeGradient = remember {
        Brush.verticalGradient(
            0f to Color.Transparent,
            0.5f to Color.Black,
            1f to Color.Transparent
        )
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> getItem(index + visibleItemsMiddle) }
            .distinctUntilChanged()
            .collect { item -> state.selectedItem = item }
    }

    Box(modifier = modifier
    ) {

        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeightDp * visibleItemsCount)
        ) {
            items(listScrollCount) { index ->
                Text(
                    text = getItem(index),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = textStyle,
                    modifier = Modifier
                        .onSizeChanged { size -> itemHeightPixels.value = size.height }
                        .then(textModifier)
                )
            }
        }


    }

}

private fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }

@Composable
private fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }

@Composable
private fun rememberWeightPickerState() = remember { WeightPickerState() }

private class WeightPickerState {
    var selectedItem by mutableStateOf("")
}