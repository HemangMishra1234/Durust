import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripod.durust.R
import com.tripod.durust.ui.theme.bodyFontFamily
import kotlin.math.roundToInt

@Composable
fun CustomSlider() {
    var sliderPosition by remember { mutableStateOf(0f) }
    val days = sliderPosition.roundToInt()

    var sliderWidth by remember { mutableStateOf(0) }
    var thumbOffset by remember { mutableStateOf(0f) }

    val density = LocalDensity.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
                .width(200.47266.dp)
            .height(200.67271.dp)
            .padding(16.dp)
            .background(Color(0xFF6887FE))
//            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .offset {
                        IntOffset(
                            thumbOffset
                                .roundToInt()
                                .plus(3), 0
                        )
                    }
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(id = R.drawable.pointer), contentDescription =null )
                Text(
                    text = days.toString(),
                    style = TextStyle(fontSize = 16.sp, color = Color.Black)
                )
            }
        }

        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..7f,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    sliderWidth = coordinates.size.width
                    thumbOffset =
                        (sliderPosition / 7f) * sliderWidth - with(density) { 24.dp.toPx() }
                }
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(129.83636.dp)
                .height(30.56364.dp)
                .background(
                    color = Color(0xFFAED5FF),
                    shape = RoundedCornerShape(size = 9.57945.dp)
                )
//                .padding(
//                    start = 4.56164.dp,
//                    top = 4.56164.dp,
//                    end = 4.56164.dp,
//                    bottom = 4.56164.dp
//                )
        ) {
            Text(
                text = "$days Days",
                style = TextStyle(
                    fontSize = 14.6.sp,
//                    lineHeight = 31.93.sp,
                    fontFamily = bodyFontFamily,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF454547),
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomSliderPreview() {
    Box(modifier = Modifier.fillMaxSize()
        .background(color = Color(0xFF6887FE))) {
        CustomSlider()
    }
}
