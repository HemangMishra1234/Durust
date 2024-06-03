package com.tripod.durust.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tripod.durust.data.AlarmItem
import com.tripod.durust.data.AlarmSchedular
import java.time.LocalDateTime

@Composable
fun AlarmUI(
    schedular: AlarmSchedular
){
    var secondsText by remember {
        mutableStateOf("")
    }
    var message by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = secondsText, onValueChange = {
            secondsText = it
        }, label = { Text("Seconds") })
        OutlinedTextField(value = message, onValueChange = {
            message = it
        }, label = {
            Text(text = "Message")
        })
        Button(onClick = {
            MainActivity.alarmItem = AlarmItem(LocalDateTime.now().plusSeconds(
                secondsText.toLong()
            ), message)
            MainActivity.alarmItem?.let(schedular::schedule)
            secondsText = ""
            message = ""
        }) {
            Text("Schedule")
        }
        OutlinedButton(onClick = {
            MainActivity.alarmItem = AlarmItem(LocalDateTime.now().plusSeconds(
                secondsText.toLong()
            ), message)
            MainActivity.alarmItem?.let(schedular::cancel)
        }) {
            Text("Cancel")
        }
    }
}