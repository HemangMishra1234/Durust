import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripod.durust.R
import com.tripod.durust.presentation.datacollection.ChatComponentViewModel
import com.tripod.durust.ui.theme.bodyFontFamily
import kotlinx.coroutines.delay


@Composable
fun ChatInputTextField(
    value: String,
    isActive: Boolean,
    placeholder: String = "Enter name here",
    onValueChange: (String) -> Unit
) {

    var valueIn by remember {
        mutableStateOf(value)
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 16.dp))
            .background(Color.White)
    ) {
        OutlinedTextField(
            value = valueIn, onValueChange = {
                onValueChange(it)
                valueIn = it
            }, enabled = isActive,
            modifier = Modifier,
            placeholder = {
                Text(
                    text = placeholder,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = bodyFontFamily,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFA0A0A0),
                    ),
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledTextColor = Color.Black
            )
        )
    }
}

@Composable
fun TopChatBar(
viewModel: ChatComponentViewModel) {
    val selectedStep by viewModel.topComponentStep
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CircleItem()
        Box(
            modifier = Modifier
                .height(4.dp)
                .weight(1f)
                .background(Color(0xFFFF6F61))
        )

        (1..3).forEach { step ->
            StepItem(
                number = step,
                isSelected = selectedStep == step
            )
            if (step < 3) {
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .weight(1f)
                        .background(Color(0xFFFF6F61))
                )
            }
        }


        Box(
            modifier = Modifier
                .height(4.dp)
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFFFF6F61))
        )
        CircleItem()
    }
}

@Composable
private fun CircleItem() {
    Box(
        modifier = Modifier
            .size(16.dp)
            .background(color = Color(0xFFFF6F61), shape = CircleShape)
    )
}


@Composable
private fun StepItem(number: Int, isSelected: Boolean) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .animateContentSize(animationSpec = tween(300))
            .background(
                color = Color(0xFFFF6F61),
                shape = if (isSelected) RoundedCornerShape(16.dp) else CircleShape
            )
            .padding(8.dp)
            .then(
                if (isSelected) Modifier.width(160.dp) else Modifier.size(30.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isSelected) "$number>" else number.toString(),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            if (isSelected) {
                Text(
                    text = " Getting to know you",
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontFamily = bodyFontFamily,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFEEF6F8),
                    )
                )
            }
        }

    }
}

@Composable
fun ChatRow(imageResId: Int = R.drawable.assistanticon, chatText: String, isHtml: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .align(Alignment.Top)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Card(
            shape = RoundedCornerShape(0.dp, 16.dp, 16.dp, 16.dp),
            modifier = Modifier.fillMaxWidth(0.85f),
            colors = CardDefaults.cardColors(
                containerColor = Color(0x99AED5FF)
            )
        ) {
            if(!isHtml){
            Text(
                text = chatText,
                modifier = Modifier.padding(16.dp),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary)
            }
            else
            {
                Text(
                    text = AnnotatedString.fromHtml(chatText.trimIndent()),
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun NextImageButton(modifier: Modifier,smallSize: Int = 50, largeSize: Int = 71, onClick: () -> Unit) {
    var isSizeReduced by remember { mutableStateOf<Boolean?>(null) }
    val imageSize = animateDpAsState(if (isSizeReduced == true) smallSize.dp else largeSize.dp)

    LaunchedEffect(key1 = isSizeReduced) {
        if (isSizeReduced == true) {
            delay(10L) // delay for 1 second
            isSizeReduced = null
        }
    }

    Image(
        painter = painterResource(id = R.drawable.nextbutton),
        contentDescription = "image description",
        contentScale = ContentScale.None,
        modifier = modifier
            .padding(1.dp)
            .width(imageSize.value)
            .height(imageSize.value)
            .clip(CircleShape)
            .clickable {
                isSizeReduced = true
                onClick()
            }
    )
}
