package com.tripod.durust.presentation.datacollection

import com.tripod.durust.data.DateEntity

sealed class OnBoardingChatComponent {
    data class NameInputText(
        val id: String,
        val message: String = "What’s your full name?",
        val response: String = ""
    ) : OnBoardingChatComponent()

    data class GenderInputUI(
        val id: String,
        val message: String = "What’s your gender?",
        val gender: GenderEntity = GenderEntity.Male
    ) : OnBoardingChatComponent()

    data class WeightInput(
        val id: String,
        val message: String = "What’s your weight?",
        val weight: Int = 65
    ) : OnBoardingChatComponent()

    data class BirthdayInput(
        val id: String,
        val message: String = "When is your birthday?",
        val date: DateEntity = DateEntity(5, 6, 2005)
    ) : OnBoardingChatComponent()

    data class HeightInput(
        val id: String,
        val message: String = "What’s your height?",
        val height: String = ""
    ) : OnBoardingChatComponent()

    data class ExerciseFrequencyInput(
        val id: String,
        val message: String = "How many days of the week do you do exercise?",
        val frequency: Int = 0
    ) : OnBoardingChatComponent()

    data class PreferredExerciseInput(
        val id: String,
        val message: String = "What’s your preferred exercise?",
        val exercise: ExerciseType = ExerciseType.WALKING
    ) : OnBoardingChatComponent()

    data class StepCountInput(
        val id: String,
        val message: String = "What's your daily average step count?",
        val steps: StepsInputEntity = StepsInputEntity.GREATER_THAN_TEN_THOUSAND
    ) : OnBoardingChatComponent()

    data class MealPreference(
        val id: String,
        val message: String = "What’s your meal preference?",
        val preference: MealPreferenceEntity = MealPreferenceEntity.VEGETARIAN
    ) : OnBoardingChatComponent()

    data class CalorieIntake(
        val id: String,
        val message: String = "What’s your daily calorie intake?",
        val calories: Int = 0
    ) : OnBoardingChatComponent()

    data class SleepSchedule(
        val id: String,
        val message: String = "What’s your sleep schedule?",
        val schedule: WakeSleepEntity = WakeSleepEntity(
            TimeEntity(6, 15),
            TimeEntity(11, 15)
        )
    ) : OnBoardingChatComponent()

    data class HealthIssues(
        val id: String,
        val message: String = "Do you have any health issues?",
        val issues: HealthCondition = HealthCondition.NONE
    ) : OnBoardingChatComponent()

    data class RoutineCheckUpFrequency(
        val id: String,
        val message: String = "How frequent are your routine check-ups?",
        val frequency: CheckUpFrequency = CheckUpFrequency.NEVER
    ) : OnBoardingChatComponent()
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
    PreferredExercise,
    StepCount,
    MealPreference,
    CalorieIntake,
    SleepSchedule,
    HealthIssues,
    RoutineCheckUpFrequency
}
