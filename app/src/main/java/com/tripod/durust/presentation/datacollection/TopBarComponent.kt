import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
fun Stepper(selectedStep :Int = 1   ) {
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
fun CircleItem() {
    Box( modifier = Modifier
        .size(16.dp)
        .background(color = Color(0xFFFF6F61), shape = CircleShape))
}

@Composable
fun StepItem(number: Int, isSelected: Boolean) {
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
fun ChatRow(imageResId: Int, chatText: String) {
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
            .clickable {
                onClick()
                isEnabled = !isEnabled
            }
    )
}

@Composable
private fun GenderCard(
    gender: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        when (gender) {
            "Male" ->  Color(0xFF9BDBFF)
            "Female" ->  Color(0xFFFF9BF5)
            "Other" -> Color(0xFFFDDC9E)
            else -> Color.LightGray
        }
    } else {
        when (gender) {
            "Male" ->  Color(0x80ABE1FF)
            "Female" ->  Color(0x80FF9BF5)
            "Other" -> Color(0x80FDDC9E)
            else -> Color.LightGray
        }
    }
    val imageResId = when(gender){
        "Male" -> R.drawable.maleicon
        "Female"-> R.drawable.othersicon
        else -> R.drawable.othersicon
    }
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(0.73617.dp)
            .width(98.05898.dp)
            .height(125.14806.dp)
            .clickable { onClick() }
            .border(if (isSelected) 2.dp else 0.dp, Color(0xFF151F55), RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier
                .background(backgroundColor)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = gender,
                style = TextStyle(
                    fontSize = 11.78.sp,
                    lineHeight = 15.46.sp,
                    fontFamily = bodyFontFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF454547),
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Replace the icon with your own drawables
            Icon(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )

        }
    }
}

@Composable
fun GenderSelection(selectedGenderIn: String? = null,isClickable: Boolean, onClick: (selectedGender: String?) -> Unit) {
    var selectedGender by remember { mutableStateOf(selectedGenderIn) }
    Row(
        modifier = Modifier
            .shadow(
                elevation = 3.5.dp,
                spotColor = Color(0x40000000),
                ambientColor = Color(0x40000000)
            )
            .border(
                width = 1.dp,
                color = Color(0xFFEEF6F8),
                shape = RoundedCornerShape(size = 12.5.dp)
            )
            .width(329.dp)
            .height(153.dp)
            .padding(start = 9.dp, top = 13.49998.dp, end = 8.70682.dp, bottom = 13.37389.dp)
                ,
        horizontalArrangement = Arrangement.Center
    ) {
        GenderCard(
            gender = "Male",
            isSelected = selectedGender == "Male",
            onClick =  { if(isClickable) {
                selectedGender = "Male"
                onClick(selectedGender)
            }}
        )
        Spacer(modifier = Modifier.width(9.dp))
        GenderCard(
            gender = "Female",
            isSelected = selectedGender == "Female",
            onClick = { if(isClickable) {
                selectedGender = "Female"
                onClick(selectedGender)
            }}
        )
        Spacer(modifier = Modifier.width(9.dp))
        GenderCard(
            gender = "Other",
            isSelected = selectedGender == "Other",
            onClick = { if(isClickable) {
                selectedGender = "Other"
                onClick(selectedGender)
            }}
        )
    }
}
