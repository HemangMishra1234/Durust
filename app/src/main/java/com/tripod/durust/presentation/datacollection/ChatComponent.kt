package com.tripod.durust.presentation.datacollection

import com.tripod.durust.data.DateEntity
import java.util.Date

sealed class ChatComponent{
    data class NameInputText(val id: String,val message: String = "What’s your full name?", val response: String = ""): ChatComponent()
    data class GenderInputUI(val id: String,val message: String = "What’s your gender?", val gender: GenderEntity = GenderEntity.Male): ChatComponent()
    data class WeightInput(val id: String,val message: String = "What’s your weight?", val weight: Int = 65): ChatComponent()
    data class BirthdayInput(val id: String,val message: String = "When is your birthday?", val date: DateEntity = DateEntity(5,6,2005)): ChatComponent()
    data class HeightInput(val id: String,val message: String = "What’s your height?", val height: String = ""): ChatComponent()
    data class ExerciseFrequencyInput(val id: String,val message: String = "How many days of the week do you do exercise?", val frequency: Int = 0): ChatComponent()
    data class PreferedExerciseInput(val id: String,val message: String = "What’s your preferred exercise?", val exercise: String = ""): ChatComponent()
    data class StepCountInput(val id: String,val message: String = "What's your daily average step count?", val steps: Int = 0): ChatComponent()
    data class MealPreference(val id: String,val message: String = "What’s your meal preference?", val preference: String = ""): ChatComponent()
    data class CalorieIntake(val id: String,val message: String = "What’s your daily calorie intake?", val calories: Int = 0): ChatComponent()
    data class SleepSchedule(val id: String,val message: String = "What’s your sleep schedule?", val schedule: String = ""): ChatComponent()
    data class HealthIssues(val id: String,val message: String = "Do you have any health issues?", val issues: HealthCondition? = null): ChatComponent()
    data class RoutineCheckUpFrequency(val id: String,val message: String = "How frequent are your routine check-ups?", val frequency: CheckUpFrequency = CheckUpFrequency.NEVER): ChatComponent()
}

data class ChatComponentEntity(
    val id: String,
    val type: ChatComponentType,
    val content: String
)

enum class ChatComponentType {
    Name,
    Gender,
    Weight,
    Birthday,
    Height,
    ExerciseFrequency,
    PreferedExercise,
    StepCount,
    MealPreference,
    CalorieIntake,
    SleepSchedule,
    HealthIssues,
    RoutineCheckUpFrequency
}
