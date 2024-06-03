package com.tripod.durust.presentation.datacollection

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripod.durust.data.DateEntity
import com.tripod.durust.ui.theme.bodyFontFamily
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Preview(showBackground = true)
@Composable
fun DatePickerCardPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF7788F4)),
        contentAlignment = Alignment.Center) {
//        DatePickerCard(
//            initialDate = DateEntity(year = 1990, month = 1, day = 1),
//            isEnabled = true,
//            onDateChanged = {}
//        )
        HeightPickerCard()
    }
}


@Composable
fun DatePickerCard(initialDate : DateEntity,isEnabled: Boolean, onDateChanged:(DateEntity)->Unit) {
    val years = remember { (1900..2100).map { it.toString() } }
    val months = remember { listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec") }
    val days = remember { (1..31).map { it.toString() } }

    val yearState = rememberDatePickerState()
    val monthState = rememberDatePickerState()
    val dayState = rememberDatePickerState()

    LaunchedEffect(key1 = yearState.selectedItem, key2 = monthState.selectedItem, key3 = dayState.selectedItem) {
        try {
            Log.i(
                "DatePickerCard",
                "yearState: ${yearState.selectedItem}, monthState: ${monthState.selectedItem}, dayState: ${dayState.selectedItem}"
            )
        onDateChanged(DateEntity(year = yearState.selectedItem.toInt(), month = months.indexOf(monthState.selectedItem) + 1, day = dayState.selectedItem.toInt()))
        }catch (_: Exception){
        }
    }

    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF7788F4),
        ),
        modifier = Modifier
            .shadow(
                elevation = 3.3796792030334473.dp,
                spotColor = Color.Black,
                ambientColor = Color(0x40000000),
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                BorderStroke(width = 0.75598.dp, color = Color(0xFFEEF6F8)),
                shape = RoundedCornerShape(size = 10.dp)
            )
            .width(316.dp)
            .height(198.06699.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                HeadingCard(text = "Year")
                HeadingCard(text = "Month")
                HeadingCard(text = "Day")
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .shadow(
                        elevation = 5.dp,
                        spotColor = Color(0x40000000),
                        ambientColor = Color(0x40000000)
                    )
                    .width(299.36844.dp)
                    .height(145.14833.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(size = 9.44976.dp))
            ) {
                if(isEnabled) {
                    DatePicker(
                        items = years,
                        state = yearState,
                        visibleItemsCount = 3,
                        textStyle = TextStyle(fontSize = 15.sp),
                        modifier = Modifier.weight(1f),
                        startIndex = initialDate.year - 1900,
                        isEnabled = isEnabled
                    )
                    DatePicker(
                        modifier = Modifier.weight(1f),
                        items = months,
                        state = monthState,
                        startIndex = initialDate.month - 1,
                        visibleItemsCount = 3,
                        textStyle = TextStyle(fontSize = 15.sp),
                        isEnabled = isEnabled
                    )
                    DatePicker(
                        modifier = Modifier.weight(1f),
                        items = days,
                        state = dayState,
                        startIndex = initialDate.day - 1,
                        visibleItemsCount = 3,
                        textStyle = TextStyle(fontSize = 15.sp),
                        isEnabled = isEnabled
                    )
                }else{
                    Text(
                        text = initialDate.year.toString(),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = bodyFontFamily,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF454547)
                        ),
                        modifier = Modifier.weight(1f)
                        , textAlign = TextAlign.Center
                    )
                    Text(
                        text = months[initialDate.month - 1],
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = bodyFontFamily,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF454547)
                        ),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = initialDate.day.toString(),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = bodyFontFamily,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF454547)
                        ),
                        modifier = Modifier.weight(1f)
                        , textAlign = TextAlign.Center
                    )

                }
            }
        }
    }
}

@Composable
private fun HeadingCard(text: String){
    Card(
        shape = RoundedCornerShape(size = 9.44976.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFAED5FF),
        ),
        modifier = Modifier
            .width(80.dp)
            .height(26.45933.dp)
            .shadow(
                4.dp,
                spotColor = Color.Black,
                ambientColor = Color.Black,
                shape = RoundedCornerShape(9.44976.dp)
            )
    , elevation = CardDefaults.elevatedCardElevation(
        defaultElevation = 4.dp
    )){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 15.12.sp,
                    fontFamily = bodyFontFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF454547)
                    ,
                )
            )
        }
    }
}




@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    items: List<String>,
    state: DatePickerState = rememberDatePickerState(),
    startIndex: Int = 0,
    visibleItemsCount: Int = 3,
    textModifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    isEnabled: Boolean,
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


    Box(modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeightDp * visibleItemsCount + 50.dp)
                .fadingEdge(fadingEdgeGradient)
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
fun rememberDatePickerState() = remember { DatePickerState() }

class DatePickerState {
    var selectedItem by mutableStateOf("")
}