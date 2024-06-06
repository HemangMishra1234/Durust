package com.tripod.durust.presentation.datacollection

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripod.durust.ui.theme.bodyFontFamily
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class WakeSleepEntity(
    val wakeTime: TimeEntity,
    val sleepTime: TimeEntity
)

fun wakeSleepEntityToString(wakeSleepEntity: WakeSleepEntity): String {
    return "${
        wakeSleepEntity.wakeTime.hour.toString().padStart(2, '0')
    }:${wakeSleepEntity.wakeTime.minute.toString().padStart(2, '0')}," +
            "${
                wakeSleepEntity.sleepTime.hour.toString().padStart(2, '0')
            }:${wakeSleepEntity.sleepTime.minute.toString().padStart(2, '0')}"
}

fun stringToWakeSleepEntity(timeString: String): WakeSleepEntity {
    val times = timeString.split(",")
    val wakeUpTimeParts = times[0].split(":")
    val sleepTimeParts = times[1].split(":")

    val wakeUpTime = TimeEntity(wakeUpTimeParts[0].toInt(), wakeUpTimeParts[1].toInt())
    val sleepTime = TimeEntity(sleepTimeParts[0].toInt(), sleepTimeParts[1].toInt())

    return WakeSleepEntity(wakeUpTime, sleepTime)
}

fun formatTime(timeEntity: TimeEntity): String {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, timeEntity.hour)
        set(Calendar.MINUTE, timeEntity.minute)
    }
    val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return simpleDateFormat.format(calendar.time)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WakeUpTimeAndBedTime(
    initialSchedule: WakeSleepEntity,
    isEnabled: Boolean,
    onTimeChanged: (WakeSleepEntity) -> Unit
) {
    val sleepTimePickerState = rememberTimePickerState(
        initialMinute = initialSchedule.sleepTime.minute,
        initialHour = initialSchedule.sleepTime.hour,
        is24Hour = false
    )
    val wakeTimePickerState = rememberTimePickerState(
        initialMinute = initialSchedule.wakeTime.minute,
        initialHour = initialSchedule.wakeTime.hour,
        is24Hour = false
    )
    LaunchedEffect(key1 = wakeTimePickerState.hour, key2 = wakeTimePickerState.minute) {
        Log.i(
            "WakeUpTimeAndBedTime",
            "wakeTimePickerState.hour: ${wakeTimePickerState.hour}, wakeTimePickerState.minute: ${wakeTimePickerState.minute}"
        )
        onTimeChanged(
            WakeSleepEntity(
                TimeEntity(wakeTimePickerState.hour, wakeTimePickerState.minute),
                TimeEntity(sleepTimePickerState.hour, sleepTimePickerState.minute)
            )
        )
    }
    LaunchedEffect(key1 = sleepTimePickerState.hour, key2 = sleepTimePickerState.minute) {
        Log.i(
            "WakeUpTimeAndBedTime",
            "sleepTimePickerState.hour: ${sleepTimePickerState.hour}, sleepTimePickerState.minute: ${sleepTimePickerState.minute}"
        )
        onTimeChanged(
            WakeSleepEntity(
                TimeEntity(wakeTimePickerState.hour, wakeTimePickerState.minute),
                TimeEntity(sleepTimePickerState.hour, sleepTimePickerState.minute)
            )
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Wake up time",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight(400),
                color = Color(0xFFEEF6F8),
                textAlign = TextAlign.Center,
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        AnimatedVisibility(isEnabled) {
            TimePicker(state = wakeTimePickerState)
        }
        AnimatedVisibility(visible = !isEnabled){
            Text(
                text = formatTime(initialSchedule.wakeTime),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = bodyFontFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFEEF6F8)
                )
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Sleep time",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight(400),
                color = Color(0xFFEEF6F8),
                textAlign = TextAlign.Center,
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        AnimatedVisibility (isEnabled) {
            TimePicker(state = sleepTimePickerState)
        }
        AnimatedVisibility(visible = !isEnabled) {
            Text(
                text = formatTime(initialSchedule.wakeTime),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = bodyFontFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFEEF6F8)
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerBase(
    initialTime: TimeEntity,
    isEnabled: Boolean,
    is24hour : Boolean= false,
    onTimeChange: (TimeEntity) -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialMinute = initialTime.minute,
        initialHour = initialTime.hour,
        is24Hour = is24hour
    )
    LaunchedEffect(key1 = timePickerState.hour, key2 = timePickerState.minute) {
        Log.i(
            "TimePicker Base",
            "wakeTimePickerState.hour: ${timePickerState.hour}, wakeTimePickerState.minute: ${timePickerState.minute}"
        )
        onTimeChange(
            TimeEntity(timePickerState.hour, timePickerState.minute)
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        AnimatedVisibility(isEnabled) {
            TimePicker(state = timePickerState)
        }

    }
}

data class TimeEntity(
    val hour: Int = 0,
    val minute: Int = 0
)


@Preview
@Composable
fun PreviewTimePicker() {
    WakeUpTimeAndBedTime(
        initialSchedule = WakeSleepEntity(
            TimeEntity(6, 15),
            TimeEntity(11, 15)
        ),
        isEnabled = false
    ) {
    }
}
