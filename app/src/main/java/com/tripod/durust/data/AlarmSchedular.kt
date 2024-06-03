package com.tripod.durust.data

interface AlarmSchedular {
    fun schedule(alarmItem: AlarmItem)
    fun cancel(alarmItem: AlarmItem)
}