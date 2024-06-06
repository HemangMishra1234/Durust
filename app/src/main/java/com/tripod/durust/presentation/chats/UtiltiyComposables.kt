package com.tripod.durust.presentation.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Box(modifier = Modifier.align(Alignment.CenterEnd)
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
