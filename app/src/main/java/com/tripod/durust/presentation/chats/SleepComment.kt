package com.tripod.durust.presentation.chats

import com.tripod.durust.presentation.datacollection.TimeEntity
import com.tripod.durust.presentation.datacollection.WakeSleepEntity

// Function to calculate the sleep duration in minutes
fun calculateSleepDuration(wakeTime: TimeEntity, sleepTime: TimeEntity): Int {
    val wakeInMinutes = wakeTime.hour * 60 + wakeTime.minute
    val sleepInMinutes = sleepTime.hour * 60 + sleepTime.minute

    return if (sleepInMinutes >= wakeInMinutes) {
        24*60 -sleepInMinutes + wakeInMinutes
    } else {
        // Assuming that sleep time is on the next day
        (sleepInMinutes + 24 * 60) - wakeInMinutes
    }
}

// Function to analyze sleep duration and provide personalized comments
fun analyzeSleepQuality(wakeSleepEntity: WakeSleepEntity): String {
    val sleepDuration = calculateSleepDuration(wakeSleepEntity.wakeTime, wakeSleepEntity.sleepTime)
    val recommendedSleepDuration = 480 // 8 hours recommended sleep duration by National Sleep Foundation

    return "You slept for about ${sleepDuration/60} and ${sleepDuration%60} minutes. "+when {
        sleepDuration < recommendedSleepDuration - 60 -> "It seems like you're not getting quite enough sleep. Consider extending your bedtime routine to ensure you're well-rested and ready to take on the day."
        sleepDuration > recommendedSleepDuration + 60 -> "You're getting a bit more sleep than recommended. While it's good to get enough rest, oversleeping might leave you feeling groggy. Try adjusting your bedtime slightly to find your optimal sleep duration."
        else -> "Your sleep duration looks just about right! Keep up the good sleep habits for overall well-being and vitality."
    }
}
