package com.tripod.durust.presentation.chats

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripod.durust.R
import com.tripod.durust.ui.theme.bodyFontFamily


@Composable
fun BotTextField(modifier: Modifier,value: String, onValueChange: (String) -> Unit, onDone: () -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = {onValueChange(it)
                        },
        placeholder = {
            Text(
                text = "Type a message",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 21.sp,
                    fontFamily = bodyFontFamily,
                    fontWeight = FontWeight(400),
                    color =  Color(0x80EEF6F8),
                    textDecoration = TextDecoration.None
                )
            )

        },
        keyboardOptions =
        KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onDone()  }),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color(0xFFEEF6F8),
            unfocusedIndicatorColor =  Color(0x80EEF6F8),
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledTextColor = Color.Black
        ),
        textStyle = TextStyle(
            fontSize = 16.sp,
            lineHeight = 21.sp,
            fontFamily = bodyFontFamily,
            fontWeight = FontWeight(400),
            color =  Color(0xFFEEF6F8),
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    )
}

@Composable
fun Response(
    response: String
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier
            .align(Alignment.CenterEnd)
            .clip(RoundedCornerShape(16.dp, 0.dp, 16.dp, 16.dp))
            .background(Color(0xFFEEF6F8))) {
            Text(
                text = response,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    fontFamily = bodyFontFamily,
                    fontWeight = FontWeight(400),
                    color = Color.Black,
                    textDecoration = TextDecoration.None
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OptionBase(
    isEnabled: Boolean,
    trueLabel: String,
    falseLabel: String,
    initialSelectedOption: Boolean?,
    onOptionSelected: (Boolean) -> Unit
) {
    var selectedOption by remember { mutableStateOf(initialSelectedOption) }
    FlowRow(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
            OptionItem(
                option = true,
                isSelected = true == selectedOption,
                isEnabled = isEnabled,
                falseLabel = falseLabel,
                trueLabel = trueLabel,
                onOptionSelected = {
                    selectedOption = it
                    onOptionSelected(it)
                }
            )
        OptionItem(
            option = false,
            isSelected = false == selectedOption,
            isEnabled = isEnabled,
            trueLabel = trueLabel,
            falseLabel = falseLabel,
            onOptionSelected = {
                selectedOption = it
                onOptionSelected(it)
            }
        )
        }
    }


@Composable
private fun OptionItem(
    option: Boolean,
    trueLabel: String,
    falseLabel: String,
    isSelected: Boolean,
    isEnabled: Boolean,
    onOptionSelected: (Boolean) -> Unit
) {
    Box(modifier = Modifier
        .clip(RoundedCornerShape(16.dp))
        .widthIn(min = 80.dp)
        .background(Color(0xFFEEF6F8))
        .animateContentSize()
        .clickable { if (isEnabled) onOptionSelected(option) }
        .border(
            border = BorderStroke(
                if (isSelected) 1.dp else 0.dp, Color.Gray
            ), shape = RoundedCornerShape(16.dp)
        )
    , contentAlignment = Alignment.Center)
    {
        Text(
            text = if(option) trueLabel else falseLabel,
            style = TextStyle(
                fontSize = if (isSelected) 14.sp else 12.sp,
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight(400),
                color = Color(0xFF454547),
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}


//@Composable
//fun ImageRow(imageID: Int = R.drawable., assistantIcon: Int = R.drawable.assistanticon, chatText: String, isAI: Boolean = false, isHtml: Boolean = false) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//    ) {
//        Image(
//            painter = painterResource(id = assistantIcon),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .size(40.dp)
//                .clip(CircleShape)
//                .align(Alignment.Top)
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Card(
//            shape = RoundedCornerShape(0.dp, 16.dp, 16.dp, 16.dp),
//            modifier = Modifier.fillMaxWidth(0.95f),
//            colors = CardDefaults.cardColors(
//                containerColor = Color(0x99AED5FF)
//            )
//        ) {
//
//                Text(
//                    text = "Data entry successful!",
//                    modifier = Modifier.padding(16.dp),
//                    fontSize = if(!isAI) 16.sp else 13.sp,
//                    color = MaterialTheme.colorScheme.onPrimary)
//            Image(painter = imageID, contentDescription = null)
//
//
//        }
//    }
//}
