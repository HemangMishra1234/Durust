import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import com.tripod.durust.R
import com.tripod.durust.ui.theme.DurustTheme
import com.tripod.durust.ui.theme.bodyFontFamily


@Composable
fun NameInputTextField(name: String, isActive: Boolean, onNameChange: (String)->Unit){
    var nameIn by remember {
        mutableStateOf(name)
    }
    Box(modifier = Modifier
        .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 16.dp))
        .background(Color.White)
    ){
        OutlinedTextField(value = nameIn, onValueChange = {
            onNameChange(it)
            nameIn = it
        }, enabled = isActive,
            modifier = Modifier,
            placeholder = {
                Text(
                    text = "Enter your name",
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
fun TopChatBar(selectedStep :Int = 1   ) {
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
    Box( modifier = Modifier
        .size(16.dp)
        .background(color = Color(0xFFFF6F61), shape = CircleShape))
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
        ){
            Text(
                text = if(isSelected) "$number>" else number.toString(),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            if(isSelected){
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
fun ChatRow(imageResId: Int = R.drawable.assistanticon, chatText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Card(
            shape = RoundedCornerShape(0.dp, 16.dp, 16.dp, 16.dp),
            modifier = Modifier.fillMaxWidth(0.85f),
            colors =  CardDefaults.cardColors(
                    containerColor =  Color(0x99AED5FF)
            )
        ) {
            Text(
                text = chatText,
                modifier = Modifier.padding(16.dp),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun NextImageButton(isEnabledT: Boolean,modifier: Modifier, onClick:() -> Unit){
    var isEnabled by remember { mutableStateOf(isEnabledT) }
    Image(
        painter = painterResource(id = R.drawable.nextbutton),
        contentDescription = "image description",
        contentScale = ContentScale.None,
        modifier = modifier
            .padding(1.dp)
            .width(if (isEnabled) 71.dp else 50.dp)
            .animateContentSize(animationSpec = tween(300))
            .height(if (isEnabled) 71.dp else 50.dp)
            .clip(CircleShape)
            .clickable {
                if(isEnabled)
                onClick()
            }
    )
}
